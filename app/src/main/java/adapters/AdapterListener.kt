package adapters

import retrofit.User

interface AdapterListener {
    fun removeUser(user: User) {}
}