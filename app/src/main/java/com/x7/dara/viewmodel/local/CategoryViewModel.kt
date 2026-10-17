package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.R
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.model.database.transaction.TransactionType
import com.x7.dara.repository.local.CategoryDaoRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.platform.PlatformRegistry.applicationContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<List<CategoryEntity>>>(
            DataUiState(isLoading = true)
        )

    val uiState: StateFlow<DataUiState<List<CategoryEntity>>> =
        _uiState.asStateFlow()

    init {
        observeCategories()
        createDefaultCategories()
    }

    private fun observeCategories() {
        viewModelScope.launch {
            categoryRepository
                .getAll()
                .collect { categories ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = categories,
                            error = null
                        )
                    }
                }
        }
    }

    private fun createDefaultCategories() {

        viewModelScope.launch {

            try {

                val defaultCategories = listOf(
//Expense
                    CategoryEntity(
                        name = "سلامت",
                        icon = R.drawable.health,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "تفریح و سرگرمی",
                        icon = R.drawable.emoji_happy,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "کافه و رستوران",
                        icon = R.drawable.task_square,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "خانه",
                        icon = R.drawable.home_outlined,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "خرید روزمره",
                        icon = R.drawable.buy_crypto,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "آموزش",
                        icon = R.drawable.teacher,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "هدایا",
                        icon = R.drawable.gift,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "سایر",
                        icon = R.drawable.more_2,
                        type = TransactionType.EXPENSE,
                        isDefault = true
                    ),
//Income
                    CategoryEntity(
                        name = "حقوق",
                        icon = R.drawable.money_change,
                        type = TransactionType.INCOME,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "هدیه",
                        icon = R.drawable.gift,
                        type = TransactionType.INCOME,
                        isDefault = true
                    ),

                    CategoryEntity(
                        name = "سایر",
                        icon = R.drawable.more_2,
                        type = TransactionType.INCOME,
                        isDefault = true
                    )
                )



                defaultCategories.forEach { category ->

                    categoryRepository.insertIfNotExists(category = category)

                    categoryRepository.updateDefaultCategoryIcon(
                        name = category.name,
                        type = category.type,
                        icon = category.icon
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun getByType(
        type: TransactionType
    ): Flow<List<CategoryEntity>> {
        return categoryRepository.getByType(type)
    }

    fun addCategory(
        name: String,
        icon: Int,
        type: TransactionType,
        isDefault: Boolean = false
    ) {

        viewModelScope.launch {

            try {

                val existingCategory =
                    categoryRepository.getByNameAndType(
                        name = name,
                        type = type
                    )

                if (existingCategory != null) {
                    return@launch
                }

                val category = CategoryEntity(
                    name = name,
                    icon = icon,
                    type = type,
                    isDefault = isDefault
                )

                categoryRepository.insert(category)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateCategory(
        category: CategoryEntity
    ) {

        viewModelScope.launch {

            try {

                categoryRepository.update(category)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun deleteCategory(
        category: CategoryEntity
    ) {

        viewModelScope.launch {

            try {

                categoryRepository.delete(category)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun clearError() {

        _uiState.update {
            it.copy(
                error = null
            )
        }
    }
}