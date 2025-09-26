fun main(){
    var computerChoice: String = ""
    print("Rock, Paper or Scissors? Enter your choice! : ")
    var playerChoice: String = readln()
    println("Player Choice: ${playerChoice}")
    val randomNumber = (1..3).random()
    if (randomNumber == 1){
        computerChoice = "Rock"
    } else if (randomNumber == 2){
        computerChoice = "Paper"
    } else {
        computerChoice = "Scissors"
    }
    println("Computer Choice: ${computerChoice}")

    println("You chose $playerChoice and the computer chose $computerChoice")

    if (playerChoice == computerChoice){
        println("Draw")
    }else if (
        (playerChoice == "Rock" && computerChoice == "Scissors") ||
        (playerChoice == "Paper" && computerChoice == "Rock") ||
        (playerChoice == "Scissors" && computerChoice == "Paper")
    ){
        println("You Won!")
    }else{
        println("Draw")
    }

}