package eu.anweb.adicharginghistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.anweb.adicharginghistory.EnergyConsumptionValueListAdapter.EnergyConsumptionValueViewHolder

class EnergyConsumptionValueListAdapter : ListAdapter<EnergyConsumptionValue, EnergyConsumptionValueViewHolder>(ENERGYCONSUMPTIONVALUES_COMPARATOR)
{
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): EnergyConsumptionValueViewHolder
	{
		return EnergyConsumptionValueViewHolder.create(parent)
	}

	override fun onBindViewHolder(holder: EnergyConsumptionValueViewHolder, position: Int)
	{
		val current = getItem(position)
		holder.bind(current.timeStamp.toString())
	}

	class EnergyConsumptionValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	{
		private val wordItemView: TextView = itemView.findViewById(R.id.textView)

		fun bind(text: String?)
		{
			wordItemView.text = text
		}

		companion object
		{
			fun create(parent: ViewGroup): EnergyConsumptionValueViewHolder
			{
				val view: View = LayoutInflater.from(parent.context)
					.inflate(R.layout.recyclerview_item, parent, false)
				return EnergyConsumptionValueViewHolder(view)
			}
		}
	}

	companion object
	{
		private val ENERGYCONSUMPTIONVALUES_COMPARATOR =
			object : DiffUtil.ItemCallback<EnergyConsumptionValue>()
			{
				override fun areItemsTheSame(
					oldItem: EnergyConsumptionValue,
					newItem: EnergyConsumptionValue
				): Boolean
				{
					return oldItem === newItem
				}

				override fun areContentsTheSame(
					oldItem: EnergyConsumptionValue,
					newItem: EnergyConsumptionValue
				): Boolean
				{
					return oldItem.timeStamp.toString() == newItem.timeStamp.toString()
				}
			}
	}
}