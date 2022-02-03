package eu.anweb.adicharginghistory

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class EnergyConsumptionValueRepository(private val energyConsumptionValueDao: EnergyConsumptionValueDao)
{
	// Room executes all queries on a separate thread.
	// Observed Flow will notify the observer when the data has changed.
	val allEnergyConsumptionValues: Flow<List<EnergyConsumptionValue>> =
		energyConsumptionValueDao.getOrderedEnergyConsumptionValues()

	// By default Room runs suspend queries off the main thread, therefore, we don't need to
	// implement anything else to ensure we're not doing long running database work
	// off the main thread.
	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun insert(energyConsumptionValue: EnergyConsumptionValue)
	{
		energyConsumptionValueDao.insert(energyConsumptionValue)
	}
}