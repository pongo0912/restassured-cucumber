@token
Feature: The ability to create and retrieve new pets

  @test
  Scenario Outline: As a user I want to be able to create a new pet and check it can be retrieved successfully
    Given I create a new pet with a unique ID and the name "<petName>" status "<status>"
    And a 200 status code is returned
    When I retrieve the created pet
    Then a 200 status code is returned
    And The response contains the field "name" with the value "<petName>"
    And The response contains the field "status" with the value "<status>"
    And The response contains the field "id" with the correct value for the random "ID"

    Examples:
      | petName | status    |
      | test    | available |

  Scenario Outline: As a user I want to be able to create a random new pet and check it can be retrieved successfully
    Given I create a new pet with a unique ID and name with status "<status>"
    And a 200 status code is returned
    Then I retrieve the created pet
    And a 200 status code is returned
    And The response contains the field "status" with the value "<status>"
    And The response contains the field "id" with the correct value for the random "ID"
    And The response contains the field "name" with the correct value for the random "Name"

    Examples:
      | status    | statusCode |
      | available | 200        |




