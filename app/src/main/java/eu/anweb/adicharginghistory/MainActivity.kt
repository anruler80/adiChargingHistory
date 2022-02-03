package eu.anweb.adicharginghistory

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime


class MainActivity : AppCompatActivity()
{
	private val newEnergyConsumptionValueActivityRequestCode = 1
	private val energyConsumptionValueViewModel: EnergyConsumptionValueViewModel by viewModels {
		EnergyConsumptionValueViewModelFactory((application as EnergyConsumptionValuesApplication).repository)
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
		val adapter = EnergyConsumptionValueListAdapter()
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(this)

		val fab = findViewById<FloatingActionButton>(R.id.fab)
		fab.setOnClickListener {
			val intent = Intent(this@MainActivity, NewEnergyConsumptionValueActivity::class.java)
			startActivityForResult(intent, newEnergyConsumptionValueActivityRequestCode)
		}

	// Add an observer on the LiveData returned by getAlphabetizedWords.
		// The onChanged() method fires when the observed data changes and the activity is
		// in the foreground.
		energyConsumptionValueViewModel.allEnergyConsumptionValues.observe(this)
		{
				energyConsumptionValues ->
			// Update the cached copy of the words in the adapter.
			energyConsumptionValues.let { adapter.submitList(it) }
		}

	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
	{
		super.onActivityResult(requestCode, resultCode, data)

		if(requestCode == newEnergyConsumptionValueActivityRequestCode && resultCode == Activity.RESULT_OK)
		{
			data?.getStringExtra(NewEnergyConsumptionValueActivity.EXTRA_REPLY)?.let {
				val temp = EnergyConsumptionValue(
					LocalDateTime.now().plusSeconds(2),
					"s0-adapter-tiefgarage.fritz.box",
					2,
					0,
					2000
				)
				energyConsumptionValueViewModel.insert(temp)
			}
		} else
		{
			Toast.makeText(
				applicationContext,
				R.string.empty_not_saved,
				Toast.LENGTH_LONG
			).show()
		}
	}
}