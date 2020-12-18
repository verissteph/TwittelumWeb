package br.com.caelum.twittelumappweb.modelo

data class TweetComUsuario(
        val mensagem:String,
        val foto:String?,
        val dono:Usuario,
        val latitude:Double,
        val longitude:Double) {

}
fun Tweet.toTweetComUsuario(usuario: Usuario):TweetComUsuario {
    return TweetComUsuario(this.mensagem,this.foto,usuario,this.latitude,this.longitude)
}