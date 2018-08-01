package model

import annotation.Model

@Model
class User {

    String username
    String firstName
    String lastName

    void sayHello() {
        println 'Hello, my name is ' + username
    }
}
