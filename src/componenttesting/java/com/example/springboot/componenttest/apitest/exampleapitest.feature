Feature: HelloController /index-info API

  Background:
    * url baseUrl

  Scenario: GET /index-info returns 200 and a string body
    Given path 'index-info'
    When method get
    Then status 200
    And match response.message == '#string'
    * print 'response from /index-info ->', response
