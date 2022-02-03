package eu.anweb.adicharginghistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers

@Database(entities = [EnergyConsumptionValue::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EnergyConsumptionValueDB : RoomDatabase()
{
    abstract fun energyConsumptionValueDao(): EnergyConsumptionValueDao

    companion object
    {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EnergyConsumptionValueDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): EnergyConsumptionValueDB
        {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EnergyConsumptionValueDB::class.java,
                    "EnergyConsumptionValue_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(EnergyConsumptionValueDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class EnergyConsumptionValueDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback()
        {
            override fun onCreate(db: SupportSQLiteDatabase)
            {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.energyConsumptionValueDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(energyConsumptionValueDao: EnergyConsumptionValueDao)
        {
            // Delete all content here.
            energyConsumptionValueDao.deleteAll()

            // Add sample words.
            var temp = EnergyConsumptionValue(LocalDateTime.now(), "s0-adapter-tiefgarage.fritz.box", 1, 2, 2000)
            energyConsumptionValueDao.insert(temp)
            temp = EnergyConsumptionValue(LocalDateTime.now().plusSeconds(2), "s0-adapter-tiefgarage.fritz.box", 2, 0, 2000)
            energyConsumptionValueDao.insert(temp)
            temp = EnergyConsumptionValue(LocalDateTime.now().plusSeconds(2), "s0-adapter-tiefgarage.fritz.box", 3, 0, 2000)
            energyConsumptionValueDao.insert(temp)
            temp = EnergyConsumptionValue(LocalDateTime.now().plusSeconds(2), "s0-adapter-tiefgarage.fritz.box", 4, 0, 2000)
            energyConsumptionValueDao.insert(temp)
        }
    }
}