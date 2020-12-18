package br.com.caelum.twittelumappweb.modelo

data class Tweet(val mensagem: String,
                 val foto: String?,
                 val latitude: Double,
                 val longitude: Double,
                 val id: Long = 0,
                ) {

    override fun toString(): String {
        return mensagem
    }

}