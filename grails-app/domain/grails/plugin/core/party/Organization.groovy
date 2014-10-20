package grails.plugin.core.party

import grails.plugin.core.taxonomy.*

class Organization extends Party {

  String name

  Term type

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static mapping = {
    sort name: 'desc' 
  }

  static constrains = {
    name (nullable: false, blank: false)
    type (nullable: false)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}