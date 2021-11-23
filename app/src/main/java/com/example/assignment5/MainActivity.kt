package com.example.assignment5

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.text.trimmedLength
import com.example.assignment5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var wordToGuess = ""
    var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.isEnabled = true
        binding.root.getViewById(R.id.button_start).setOnClickListener(StartGame())

        val letterButtonIds = listOf(R.id.button_letter_1, R.id.button_letter_2, R.id.button_letter_3, R.id.button_letter_4)
        for(id in letterButtonIds) {
            val button = binding.root.getViewById(id) as Button
            button.isEnabled = false
            button.setOnClickListener(GuessLetter(button))
        }

        binding.buttonSurrender.isEnabled = false
        binding.root.getViewById(R.id.button_surrender).setOnClickListener(StopGame())

        binding.buttonCheck.isEnabled = false
        binding.root.getViewById(R.id.button_check).setOnClickListener(Check())
    }

    private inner class StartGame : View.OnClickListener {
        override fun onClick(v: View?) {
            wordToGuess = wordList[(0..10).random()]
            Log.i("TESTING", wordToGuess.toString())
            binding.buttonStart.isEnabled = false
            val letterButtonIds = listOf(R.id.button_letter_1, R.id.button_letter_2, R.id.button_letter_3, R.id.button_letter_4)
            for(id in letterButtonIds) {
                val button : Button = binding.root.getViewById(id) as Button
                button.isEnabled = true
                button.text = getString(R.string.letter_button_filler)
            }
            binding.buttonSurrender.isEnabled = true
            binding.buttonCheck.isEnabled = true
            binding.textFeedback.text = getString(R.string.start_game_message)
        }
    }

    private inner class GuessLetter(private val button : Button) : View.OnClickListener {
        override fun onClick(v: View?) {
            val text = binding.edittextLetter.text
            if (text.isEmpty()) {
                binding.textFeedback.text = getString(R.string.empty_string_message)
                binding.textFeedback.setTextColor(Color.RED)
                return
            }
            if (text.trimmedLength() > 1) {
                binding.textFeedback.text = getString(R.string.long_string_message)
                binding.textFeedback.setTextColor(Color.RED)
                return
            }
            if ("^[0-9]*$".toRegex().matches(text)) {
                binding.textFeedback.text = getString(R.string.number_string_message)
                binding.textFeedback.setTextColor(Color.RED)
                return
            }
            binding.textFeedback.text = ""
            binding.textFeedback.setTextColor(Color.BLACK)
            button.text = text
            guessCount += 1
        }
    }

    private inner class Check : View.OnClickListener {
        override fun onClick(v: View?) {
            val guess = mutableListOf<String>()
            val letterButtonIds = listOf(R.id.button_letter_1, R.id.button_letter_2, R.id.button_letter_3, R.id.button_letter_4)
            var index = 0
            var correctGuesses = 0
            for(id in letterButtonIds) {
                val button : Button = binding.root.getViewById(id) as Button
                guess.add(button.text.toString())
                if (guess[index].toString() == wordToGuess[index].toString()) {
                    correctGuesses += 1
                } else {
                    button.text = getString(R.string.letter_button_filler)
                    binding.textFeedback.text = getString(R.string.keep_trying_message)
                    binding.textFeedback.setTextColor(Color.RED)
                }
                index += 1
            }

            if (correctGuesses == 4) {
                wordToGuess = ""
                binding.buttonStart.isEnabled = true
                for(id in letterButtonIds) {
                    val button = binding.root.getViewById(id) as Button
                    button.isEnabled = false
                }
                binding.buttonSurrender.isEnabled = false
                binding.buttonCheck.isEnabled = false
                binding.textFeedback.setTextColor(Color.BLACK)
                binding.textFeedback.text = getString(R.string.win_message, guessCount.toString())
            }
            Log.i("TESTING", wordToGuess)
        }
    }

    private inner class StopGame : View.OnClickListener {
        override fun onClick(v: View?) {
            wordToGuess = ""
            binding.buttonStart.isEnabled = true
            val letterButtonIds = listOf(R.id.button_letter_1, R.id.button_letter_2, R.id.button_letter_3, R.id.button_letter_4)
            for(id in letterButtonIds) {
                val button = binding.root.getViewById(id) as Button
                button.isEnabled = false
            }
            binding.buttonSurrender.isEnabled = false
            binding.buttonCheck.isEnabled = false
            binding.textFeedback.setTextColor(Color.BLACK)
            binding.textFeedback.text = getString(R.string.surrender_message, guessCount.toString())
        }
    }
}
val wordList = listOf(
    "with", "that", "from", "were", "this", "also", "they", "have", "been", "when",
    "into", "time", "more", "over", "only", "most", "some", "city", "such", "then",
    "made", "used", "many", "year", "part", "born", "film", "than", "team", "both",
    "well", "them", "work", "life", "area", "name", "high", "will", "four", "each",
    "same", "game", "club", "june", "july", "west", "john", "home", "town", "held",
    "york", "took", "line", "east", "song", "best", "back", "like", "show", "band",
    "main", "left", "said", "died", "last", "five", "long", "very", "park", "road",
    "army", "book", "what", "near", "just", "late", "form", "much", "side", "play",
    "down", "even", "land", "came", "went", "make", "next", "role", "king", "head",
    "list", "take", "site", "days", "lost", "live", "open", "once", "hall", "race",
    "lead", "case", "wife", "lake", "upon", "rock", "free", "good", "love", "tour",
    "away", "body", "sold", "less", "gave", "term", "fire", "does", "arts", "half",
    "help", "seen", "full", "thus", "must", "news", "able", "gold", "sent", "soon",
    "bank", "week", "base", "data", "post", "type", "paul", "size", "hill", "star",
    "goal", "runs", "real", "come", "food", "ship", "navy", "unit", "whom", "ever",
    "plan", "blue", "bill", "past", "find", "date", "here", "mark", "move", "test",
    "rest", "seat", "room", "loss", "wide", "fact", "give", "nine", "port", "lord",
    "uses", "word", "hand", "mary", "fort", "need", "rate", "fall", "turn", "deal",
    "shot", "care", "plot", "cast", "camp", "male", "done", "cost", "view", "vote",
    "face", "lies", "call", "rule", "told", "pass", "idea", "wall", "dead", "dark",
    "code", "host", "grew", "your", "lack", "ohio", "wing", "poor", "true", "farm",
    "mass", "keep", "meet", "girl", "crew", "jack", "solo", "asia", "stop", "cars",
    "join", "hold", "firm", "iron", "wood", "hard", "fell", "know", "fish", "sons",
    "feet", "duke", "tree", "arms", "rose", "iran", "draw", "ball", "rail", "zone",
    "text", "fine", "lady", "note", "kept", "paid", "says", "deep", "felt", "goes",
    "jazz", "laws", "cell", "hong", "mile", "kong", "fame", "rome", "boys", "read",
    "acts", "look", "vice", "fans", "mike", "peak", "rank", "risk", "ring", "kind",
    "holy", "want", "miss", "fund", "sets", "drug", "sign", "rise", "edge", "ways",
    "guns", "bowl", "beat", "stay", "onto", "pope", "cape", "wild", "mill", "bass",
    "ends", "wins", "rare", "pair", "boat", "core", "wind", "heat", "rear", "aged",
    "page", "kill", "jews", "card", "loan", "bell", "sale", "ncaa", "fuel", "poet",
    "rich", "mind", "roof", "cold", "hits", "laid", "tony", "mine", "earl", "flow",
    "gene", "ford", "ward", "save", "fair", "task", "flag", "spot", "hope", "khan",
    "coal", "foot", "talk", "wars", "duty", "gain", "hour", "dean", "folk", "prix",
    "link", "plus", "ones", "gets", "shop", "moth", "lane", "drew", "path", "door",
    "golf", "arab", "fast", "eyes", "jean", "bird", "mean", "iowa", "trip", "flat",
    "ages", "step", "sell", "wave", "juan", "baby", "soul", "acid", "iraq", "feel",
    "moon", "gulf", "grey", "adam", "soil", "bear", "pool", "tank", "wine", "tell",
    "grow", "none", "anne", "alan", "gone", "salt", "user", "poem", "huge", "walk",
    "rice", "ryan", "jobs", "eric", "anna", "tied", "tall", "hair", "gate", "mode",
    "safe", "easy", "skin", "bush", "kent", "dave", "carl", "bond", "feed", "alex",
    "slow", "ross", "knew", "jane", "nazi", "pick", "file", "matt", "hero", "fear",
    "twin", "snow", "nick", "rain", "cash", "fifa", "utah", "wear", "seek", "disc",
    "ride", "send", "gang", "sees", "drop", "evil", "roll", "gary", "pain", "andy",
    "seed", "jeff", "bomb", "uefa", "gray", "jury", "cave", "suit", "clay", "yard",
    "hull", "nova", "clan", "legs", "zero", "lose", "mall", "tail", "asks", "rico",
    "hunt", "sand", "sole", "cook", "fled", "fred", "mail", "else", "sang", "belt",
    "vast", "till", "karl", "debt", "copy", "soft", "ties", "jump", "eggs", "tend",
    "tool", "pole", "seem", "maps", "dogs", "cuba", "tech", "coat", "vary", "pure",
    "sing", "kids", "loop", "phil", "tons", "exit", "pink", "peru", "root", "hole"
)