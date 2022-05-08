package com.yn_1.novello_app.account;

/**
 * Enumerated type representation of the four types of user account. <br> <br>
 *
 * Four Account Types: <br>
 * 1. Admin (0): User type with same powers of moderator, as well as permissions to completely
 *  add or remove books from the server. <br>
 * 2. Moderator (1): User type that can moderate reviews and public chats. <br>
 * 3. Adult (2): Default user type. Can do standard actions. <br>
 * 4. Child (3): Restricted user type. Cannot do communication with other users.
 *
 * @author Roba Abbajabal
 */
public enum UserType {
    ADMIN, MODERATOR, ADULT, CHILD
}
