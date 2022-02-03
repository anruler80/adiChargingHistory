package eu.anweb.adicharginghistory

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EnergyConsumptionValueDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(energyConsumptionValue: EnergyConsumptionValue)

    @Delete
    suspend fun delete(energyConsumptionValue: EnergyConsumptionValue): Int

    @Query("DELETE FROM EnergyConsumptionValue")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM EnergyConsumptionValue ORDER BY timeStamp DESC")
    fun getOrderedEnergyConsumptionValues(): Flow<List<EnergyConsumptionValue>>
}