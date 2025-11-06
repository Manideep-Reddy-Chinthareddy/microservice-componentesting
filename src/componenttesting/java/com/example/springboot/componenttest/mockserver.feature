@ignore
Feature: mock server for component tests

  Background:
    * configure headers = { 'Content-Type': 'application/json' }

  Scenario: pathMatches('/api')
    * print 'mock /hello called'
    * def response = { message: 'hello' }
