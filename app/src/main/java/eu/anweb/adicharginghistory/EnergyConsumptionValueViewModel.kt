package eu.anweb.adicharginghistory

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class EnergyConsumptionValueViewModel(private val repository: EnergyConsumptionValueRepository) : ViewModel() {

	// Using LiveData and caching what allWords returns has several benefits:
	// - We can put an observer on the data (instead of polling for changes) and only update the
	//   the UI when the data actually changes.
	// - Repository is completely separated from the UI through the ViewModel.
	val allEnergyConsumptionValues: LiveData<List<EnergyConsumptionValue>> = repository.allEnergyConsumptionValues.asLiveData()

	/**
	 * Launching a new coroutine to insert the data in a non-blocking way
	 */
	public fun insert(energyConsumptionValue: EnergyConsumptionValue) = viewModelScope.launch {
		repository.insert(energyConsumptionValue)
	}
}

class EnergyConsumptionValueViewModelFactory(private val repository: EnergyConsumptionValueRepository) : ViewModelProvider.Factory
{
	override fun <T : ViewModel> create(modelClass: Class<T>): T
	{
		if(modelClass.isAssignableFrom(EnergyConsumptionValueViewModel::class.java))
		{
			@Suppress("UNCHECKED_CAST")
			return EnergyConsumptionValueViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}