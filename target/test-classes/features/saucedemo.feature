Feature: Purchase flow in SauceDemo
  As an user
  I want to be able to log in, add products to the cart, and complete a purchase
  So that I can verify the checkout process works correctly

  @standard-user @performance_glitch_user
  Scenario Outline: Successful purchase flow
    Given the user logs in with valid credentials username '<username>' and password '<password>'
    When the user adds products to the cart and proceeds to checkout
    And fills in the required checkout information
    Then the user should see the order overview and complete the purchase
    And the confirmation message "Thank you for your order!" should be displayed

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |