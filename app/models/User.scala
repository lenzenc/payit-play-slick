package models

case class User(
  id: Long,
  firstName: String,
  lastName: String,
  username: String,
  password: String)