package com.example.mymap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymap.databinding.ActivityMainBinding
import com.example.mymap.models.Place
import com.example.mymap.models.UserMap

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userMaps: MutableList<UserMap>
    private lateinit var mapAdapter: MapsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        userMaps = generateSimpleData().toMutableList()

        binding.rcvMaps.layoutManager = LinearLayoutManager(this)

        mapAdapter = MapsAdapter(this, userMaps, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                intent.putExtra(Utils.EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
            }
        })
        binding.rcvMaps.adapter = mapAdapter

        binding.btnAdd.setOnClickListener {
            val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
            AlertDialog.Builder(this).setTitle("Map title")
                .setView(mapFormView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK") { _, _ ->
                    val title = mapFormView.findViewById<EditText>(R.id.etTitleMap).text.toString()
                    if (title.trim().isEmpty()) {
                        Toast.makeText(this, "Fill out title", Toast.LENGTH_SHORT)
                            .show()
                        return@setPositiveButton
                    }
                    val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
                    intent.putExtra(Utils.EXTRA_MAP_TITLE, title)
                    getResult.launch(intent)
                }
                .show()
        }
    }

    fun generateSimpleData() : List<UserMap> {
        return listOf(
            UserMap("Đại học Cần Thơ",
                listOf(
                    Place("Trường CNTT&TT", "thuộc ĐH Cần Thơ", 10.0308541, 105.768986),
                    Place("Trường Nông Nghiệp", "thuộc ĐH Cần Thơ", 10.0302655, 105.7679642),
                    Place("Hội trường rùa", "nơi tổ chức các hoạt động...", 10.0293402, 105.7690273)
                )
            ),
            UserMap("Ẩm thực",
                listOf(
                    Place("The 80's icafe", "Đường Mạc Thiên Tích", 10.0286827, 105.7732964),
                    Place("Trà sữa Tigon", "Đường Mạc Thiên Tích", 10.0278105, 105.7718373),
                    Place("Cafe Thủy Mộc", "Đường 3/2", 10.0273775, 105.7704913)
                )
            )
        )
    }
    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val userMap = result.data?.getSerializableExtra(Utils.EXTRA_USER_MAP) as UserMap
                userMaps.add(userMap)
                mapAdapter.notifyItemInserted(userMaps.size - 1)

            }
        }
}