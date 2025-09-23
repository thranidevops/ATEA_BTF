Feature: Practice form submission

  Background:
    Given I am on the Angular practice form

  @happy
  Scenario Outline: Submit the form successfully
    When I enter name "<name>" and email "<email>" and password "<password>"
    And I check the ice cream preference "<likeIceCreams>"
    And I select gender "<gender>"
    And I choose employment status "<employment>"
    And I enter date of birth "<dob>"
    And I submit the form
    Then I should see a success message

    Examples:
      | name       | email                 | password   | likeIceCreams | gender | employment | dob        |
      | Tharani S  | tharani@example.com   | Secret@123 | yes           | Female | Employed   | 09/22/2025 |
      | Rahul Test | rahul@testmail.com    | Pass@1234  | no            | Male   | Student    | 10/10/2025 |
