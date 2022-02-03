package eu.anweb.adicharginghistory

import androidx.room.Entity
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(primaryKeys = ["timeStamp", "energyMeterUrl", "energyMeterChannelId"])
data class EnergyConsumptionValue(val timeStamp: LocalDateTime, val energyMeterUrl: String,
                                  val energyMeterChannelId: Byte, val impulseCounter: Long,
                                  val impulsesPerKwh: Short)
/*
{
    fun getKwhValue(): Float
    {
        if((this.impulseCounter != 0L) && (this.impulsesPerKwh != 0.toShort()))
            return (this.impulseCounter / this.impulsesPerKwh).toFloat()
        else
            return Float.NaN
    }
}
*/

class Converters {
  @TypeConverter
  fun fromTimestamp(value: Long?): LocalDateTime? {
    return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
  }

  @TypeConverter
  fun dateToTimestamp(date: LocalDateTime?): Long? {
    return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
  }
}