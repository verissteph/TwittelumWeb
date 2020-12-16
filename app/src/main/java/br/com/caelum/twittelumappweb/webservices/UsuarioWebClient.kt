package br.com.caelum.twittelumappweb.webservices

import br.com.caelum.twittelumappweb.modelo.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

class UsuarioWebClient(retrofit: Retrofit) {

    private val service: UsuarioService by lazy { retrofit.create(UsuarioService::class.java) }

    fun registra(usuario: Usuario,
                 sucesso: (usuario: Usuario) -> Unit,
                 falha: (Throwable) -> Unit) {
        val chamadaParaCriar = service.cria(usuario)
        chamadaParaCriar.enqueue(object : Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                falha(t)
            }
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                response.body()?.let(sucesso)
            }
        })
    }

    fun fazLogin(usuario: Usuario, sucesso: (usuario: Usuario) -> Unit, falha: (Throwable) -> Unit)
    {
        val chamadaPraLogar = service.loga(usuario)
        chamadaPraLogar.enqueue(object : Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                falha(t)
            }
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                response.body()?.let(sucesso)
                response.errorBody()?.let { falha(Throwable(it.string())) }
            }
        })



    }

    private interface UsuarioService {
        @POST("/usuario")
        fun cria(@Body usuario: Usuario): Call<Usuario>

        @POST("/usuario/login")
        fun loga(@Body usuario: Usuario): Call<Usuario>
    }

}
