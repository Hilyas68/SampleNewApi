Feature: article feature

  Scenario: return 403 for an unauthorized request
    Given Unauthorized user
    When the user requests all the articles without token
    Then forbidden status response is returned

  Scenario: A user get all articles
    Given the following articles
      | title | headline  | content  |
      | title1| headline1 | content1 |
      | title2| headline2 | content2 |
      | title3| headline3 | content3 |
    When the user requests all the articles with token
    Then all the articles are returned


  Scenario:A user post a new article
    When a user post a new article title4 with headline headline4 and content content4
    Then it is saved in the database
    And it has an id

