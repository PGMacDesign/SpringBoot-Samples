package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.datamodels.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserComplexRepository extends MongoRepository<User, String> {
    /**
     * Same as {@link ComplexQueriesSamples#findPeopleWithThisName(String)}
     */
    List<User> findByFirstName(String name);
    /**
     * Find user by username
     */
    List<User> findByUsername(String username);
    /**
     * Find user by email
     */
    List<User> findByEmail(String email);
    /**
     * Same as {@link ComplexQueriesSamples#findPeopleWithThisName(String)}
     */
    List<User> findByLastName(String name);
    /**
     * Same as {@link ComplexQueriesSamples#findPeopleWhoseNamesStartWithA()}
     */
    List<User> findByFirstNameStartingWith(String regexp);
    /**
     * Same as {@link ComplexQueriesSamples#findPeopleWhoseNamesEndWithT()}
     */
    List<User> findByFirstNameEndingWith(String regexp);
    /**
     * Same as {@link ComplexQueriesSamples#findPeopleOlderThan18AndLessThan50()}
     *     GT == Greater Than    &&    LT == Less Than
     */
    List<User> findByAgeBetween(int ageGT, int ageLT);
    /**
     * Shortcut to search for users with {name} and then order by age ascending
     */
    List<User> findByFirstNameLikeOrderByAgeAsc(String name);

    //////////////////////
    //JSON Query Methods//
    //////////////////////

    /**
     * Find users by name with a json query
     */
    @Query("{ 'name' : ?0 }")
    List<User> findUsersByFirstName(String name); //Users ??
    /**
     * Find users by name with a json query. Similar to
     * {@link ComplexQueriesSamples#findPeopleWhoseNamesStartWithA()}
     */
    @Query("{ 'firstName' : { $regex: ?0 } }")
    List<User> findUsersByRegexpFirstName(String regexp); //Users ??
    /**
     * Find users between the passed ages. Similar to
     * {@link ComplexQueriesSamples#findPeopleOlderThan18AndLessThan50()}
     *           GT == Greater Than    &&    LT == Less Than
     * {@link ComplexQueriesSamples#findPeopleWhoseNamesStartWithA()}
     */
    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
    List<User> findUsersByAgeBetween(int ageGT, int ageLT); //Users ??

    /**
     * Complex query that basically reads: Find users where the first name and last name match the regex,
     * the age is between the passed numbers and the username matches the regex of the username passed.
     * NOTE! THIS IS AN AND QUERY (&&) NOT AN OR QUERY
     */
    @Query( "{'firstName': ?0, 'lastName': ?1, 'age': { $gt: ?2, $lt: ?3}, 'username': ?4 }")
    List<User> findByRegexpFirstNameRegexpLastNameAgeBetweenUsernameRegexp(
            String firstName, String lastName, Integer startAge, Integer endAge, String username);
}
