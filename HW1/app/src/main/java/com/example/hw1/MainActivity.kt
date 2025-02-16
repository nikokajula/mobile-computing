package com.example.hw1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hw1.SampleData.ConversationScreen
import kotlinx.serialization.Serializable

@Serializable
object Profile
@Serializable
object Conversation
@Serializable
object NewMessage

@Composable
fun MyAppNavHost(
    db: AppDatabase,
    applicationContext: Context,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    ) {
    val messageDao = db.savedMessageDao()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Conversation
    ) {
        composable<Profile> {
            ProfileScreen(onNavigateToConversation = { navController.navigate(route = Conversation, navOptions = navOptions { popUpTo<Conversation>{inclusive = true} } )})
        }
        composable<Conversation> {
            ConversationScreen(
                applicationContext,
                messageDao,
                onNavigateToNewMessage = { navController.navigate(route = NewMessage) },
                onNavigateToProfile = { navController.navigate(route = Profile) }
            )
        }
        composable<NewMessage> {
            NewMessageScreen(
                applicationContext,
                messageDao,
                onNavigateToConversation = { navController.navigate(route = Conversation) },
            )
        }
    }
}


@Entity
data class SavedMessage(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "message") val message: String?,
    @ColumnInfo(name = "image") val image: String?
)

@Dao
interface SavedMessageDao {
    @Query("SELECT * FROM savedmessage")
    fun getAll(): List<SavedMessage>

    @Query("SELECT COUNT(*) FROM savedmessage")
    fun getMessageCount(): Int

    @Insert
    fun insert(vararg messages: SavedMessage)

    @Delete
    fun delete(user: SavedMessage)
}

@Database(entities = [SavedMessage::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedMessageDao(): SavedMessageDao
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "messages"
            ).allowMainThreadQueries().fallbackToDestructiveMigration()
                .build()
            MyAppNavHost(db, applicationContext)
        }
    }
}


