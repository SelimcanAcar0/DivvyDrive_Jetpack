import android.content.Context
import android.content.SharedPreferences
import com.example.divvydrivestaj.constant.SharedText

class SharedPref(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("DivvyDrivePref", Context.MODE_PRIVATE)

    fun boolKaydet(key: SharedText,value:Boolean ) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key.name, value)
        editor.apply()
    }
    fun stringKaydet(key:SharedText,value:String){
        val editor = sharedPreferences.edit()
        editor.putString(key.name,value)
        editor.apply()
    }

    fun boolAl(key: SharedText, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key.name, defaultValue)
    }
    fun stringAl(key:SharedText,value: String): String {
       return sharedPreferences.getString(key.name,value) ?: ""
    }
}