package com.enfotrix.scooringbook.Ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.enfotrix.scooringbook.R
import com.enfotrix.scooringbook.databinding.ActivityScoreBoardBinding

class ScoreBoard : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBoardBinding
    private var runs = 0
    private var balls = 0
    private var overs = 0
    private var currentBatsman = 1
    private var player1Score = 0
    private var player2Score = 0
    private var player1Balls = 0
    private var player2Balls = 0
    private var bowlerBalls = 0
    private var bowlerTotalRuns = 0
    private var bowlerOver = 0
    private lateinit var mcontext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mcontext = this
        setupClickListeners()
        currentBatsman = 1
        binding.starIndicator1.visibility = View.VISIBLE
        binding.starIndicator2.visibility = View.INVISIBLE
        updateScoreboard()
    }

    private fun setupClickListeners() {
        // Set up click listeners for different score options
        binding.zero.setOnClickListener { updateScore(0) }
        binding.one.setOnClickListener { updateScore(1) }
        binding.two.setOnClickListener { updateScore(2) }
        binding.three.setOnClickListener { updateScore(3) }
        binding.four.setOnClickListener { updateScore(4) }
        binding.six.setOnClickListener { updateScore(6) }

        // New click listeners for wide, no ball, and leg byes
//        binding.wide.setOnClickListener { handleSpecialScore("Wide") }
        binding.noBall.setOnClickListener { showNoBallDialog() }
      //  binding.legByes.setOnClickListener { handleSpecialScore("Leg Byes") }
    }

    private fun updateScore(score: Int) {
        runs += score
        balls++
        binding.Runs.text=runs.toString()
        binding.balls.text=balls.toString()

        // Update player scores and balls
        if (currentBatsman == 1) {
            player1Score += score
            player1Balls++
            binding.player1run.text = player1Score.toString()
            binding.player1balls.text = player1Balls.toString()
        } else {
            player2Score += score
            player2Balls++
            binding.player2runs.text = player2Score.toString()
            binding.player2balls.text = player2Balls.toString()
        }

        bowlerBalls++
        binding.bowlerBalls.text = bowlerBalls.toString()
        if (bowlerBalls % 6 == 0) {
            bowlerOver++
        }
        binding.bowlerover.text = bowlerOver.toString()

        // Update the bowler's runs after each ball
        bowlerTotalRuns += score
        binding.bowlerRuns.text = bowlerTotalRuns.toString()

        // Update the timeline of the over
        val timeline = "${binding.scoreDone.text} $score"
        binding.scoreDone.text = timeline.trim()

        // Check if the score is odd to rotate the strike
        if (score % 2 != 0) {
            rotateStrike()
        }

        if (balls % 6 == 0) {
            // Calculate the current run rate
            if (overs > 0) {
                val currentRunRate = (runs.toDouble() / overs).toFloat()
                binding.CRR.text = String.format("%.2f", currentRunRate)
            } else {
                binding.CRR.text = "0.00"
            }

            // Reset batsmen's scores for the next over
//            player1Score = 0
//            player2Score = 0
            bowlerBalls = 0
            binding.bowlerBalls.text = "0"
            binding.scoreDone.text = ""

            // Call updateScoreboard after resetting balls count
            updateScoreboard()
        }
    }

    private fun rotateStrike() {
        if (balls % 6 == 0) {
            val currentBatsmanScore = if (currentBatsman == 1) player1Score else player2Score
            if (currentBatsmanScore % 2 == 0 || currentBatsmanScore == 0) {
                currentBatsman = if (currentBatsman == 1) 2 else 1

                // Update the visibility of star indicators based on the current batsman
                binding.starIndicator1.visibility =
                    if (currentBatsman == 1) View.VISIBLE else View.INVISIBLE
                binding.starIndicator2.visibility =
                    if (currentBatsman == 2) View.VISIBLE else View.INVISIBLE
            }
        } else {
            // If it's not the last ball of the over, rotate the strike
            currentBatsman = if (currentBatsman == 1) 2 else 1

            // Update the visibility of star indicators based on the current batsman
            binding.starIndicator1.visibility =
                if (currentBatsman == 1) View.VISIBLE else View.INVISIBLE
            binding.starIndicator2.visibility =
                if (currentBatsman == 2) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun showNoBallDialog() {
        val builder = AlertDialog.Builder(mcontext)
        val dialogView = layoutInflater.inflate(R.layout.noball_dilaog, null)
        builder.setView(dialogView)

        val one = dialogView.findViewById<TextView>(R.id.no1)
        val two = dialogView.findViewById<TextView>(R.id.no2)
        val three = dialogView.findViewById<TextView>(R.id.no3)
        val four = dialogView.findViewById<TextView>(R.id.no4)
        val six = dialogView.findViewById<TextView>(R.id.no6)
        val zero = dialogView.findViewById<TextView>(R.id.no0)

        // Set click listeners for each button to handle the runs
        one.setOnClickListener { handleRunsOnNoBall(1) }
        two.setOnClickListener { handleRunsOnNoBall(2) }
        three.setOnClickListener { handleRunsOnNoBall(3) }
        four.setOnClickListener { handleRunsOnNoBall(4) }
        six.setOnClickListener { handleRunsOnNoBall(6) }
        zero.setOnClickListener { handleRunsOnNoBall(0) }

        val dialog = builder.create()
        dialog.show()
    }

//    private fun handleSpecialScore(specialScoreType: String) {
//        // Example: Increment runs for no ball
//        if (specialScoreType == "No Ball") {
//            runs++
//            if (currentBatsman == 1) {
//                // If player 1 is on strike, add the special score to player1Score
//                player1Score += 1
//                binding.player1run.text = player1Score.toString()
//                runs += 1
//                runs+=1
//                binding.Runs.text = runs.toString()
//            } else {
//                // If player 2 is on strike, increment player2Score and overall runs
//                player2Score += 1
//                player2Balls++
//                binding.player2runs.text = player2Score.toString()
//                runs += 1
//                binding.Runs.text = runs.toString()
//
//
//
//
//            }
////        }
//
//
//        // Update the timeline with special score type
//        val timeline = "${binding.scoreDone.text} $specialScoreType"
//        binding.scoreDone.text = timeline.trim()
//
//        // Check if the score is odd to rotate the strike
//        if (specialScoreType == "No Ball" && runs % 2 != 0) {
//            rotateStrike()
//        }
//
//        // Update the current run rate
//        val currentRunRate = if (overs > 0) (runs.toDouble() / overs).toFloat() else 0.0f
//        binding.CRR.text = String.format("%.2f", currentRunRate)
//
//        // Check if overs completed
//        if (balls % 6 == 0) {
//            updateScoreboard()
//        }
//    }

    private fun handleRunsOnNoBall(runsOnNoBall: Int) {
        runs++
        if (currentBatsman == 1) {
            player1Score += runsOnNoBall
            binding.player1run.text = player1Score.toString()
        } else {
            player2Score += runsOnNoBall
            binding.player2runs.text = player2Score.toString()
        }

        runs += runsOnNoBall
        // Check if the score is odd to rotate the strike
        if (runsOnNoBall % 2 != 0) {
            rotateStrike()
        }
        updateScoreboard()
    }

    private fun updateScoreboard() {
        binding.Runs.text = runs.toString()
        binding.balls.text = balls.toString()
        if (balls == 6) {
            overs++  // Increment the over count
            balls = 0  // Reset the ball count to zero
            binding.overs.text = overs.toString()
            binding.balls.text = balls.toString()
        }
    }
}
