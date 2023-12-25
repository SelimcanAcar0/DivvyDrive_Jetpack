package com.example.divvydrivestaj.viewmodel

import SharedPref
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.divvydrivestaj.constant.SharedText
import com.example.divvydrivestaj.entity.Kullanici
import com.example.divvydrivestaj.entity.Ticket
import com.example.divvydrivestaj.repo.IServisRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TicketVM(context: Context): ViewModel() {

    private val repository = IServisRepo()
    private val _ticket = MutableStateFlow<Ticket?>(null)
    private val sharedPref=SharedPref(context)
    private val _ticketID= MutableStateFlow("")

    val ticket: StateFlow<Ticket?> = _ticket.asStateFlow()
    val ticketID:StateFlow<String> get()= _ticketID.asStateFlow()



    fun ticketAl(kullanici: Kullanici) {
            val call = repository.ticketAl(kullanici)

            call.enqueue(object : Callback<Ticket> {
                    override fun onFailure(call: Call<Ticket>, t: Throwable) {
                        Log.e("Ticket", "call failed")
                    }

                    override fun onResponse(call: Call<Ticket>, response: Response<Ticket>) {
                        if (response.isSuccessful) {
                            _ticket.value=response.body()
                        } else {
                            Log.e("Ticket", "Erişilemedi")

                        }
                    }
                }
                )

    }

    fun ticketIDHatirla(){
            _ticketID.value=sharedPref.stringAl(SharedText.Ticket,"")
        }




    }



