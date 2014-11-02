package grails.plugin.core.party

import grails.plugin.core.taxonomy.*

class Organization extends Party {

  String name
  Term type

  static mapping = {
    sort name: 'desc' 
  }

  static constrains = {
    name (nullable: false, blank: false)
    type (nullable: false)
  }

  String toString() {
    name
  }

}