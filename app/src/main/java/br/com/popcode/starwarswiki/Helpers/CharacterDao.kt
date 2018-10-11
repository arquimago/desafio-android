package br.com.popcode.starwarswiki.helpers

import android.arch.persistence.room.*
import br.com.popcode.starwarswiki.models.Character

@Dao
interface CharacterDao{
    @Query("SELECT * FROM character")
    fun getAll() : MutableList<Character>

    @Query("SELECT * FROM character WHERE name LIKE :first")
    fun findByName(first: String) : Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg char: Character)

    @Delete
    fun delete(char: Character)

}