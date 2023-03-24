package interfaces

import user.User

interface AdapterListener {
    fun removeUser(user: User) {}
}