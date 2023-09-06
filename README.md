# A1 - Piraten Karpen

  * Author: Andrew McLaren
  * Email: mclara2@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar` 
  * To toggle logging:
    * At the top of the main file uncomment and comment-out the necessary lines.

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * Feature is tested and integrated with all features it is dependent on.

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| /   | F01 | Roll a dice | D | 2023/01/01 | 2023/01/11 |
| /   | F02 | Roll eight dices  | D |  2023/01/11 | 2023/01/11 |
| /   | F03 | end turn if three skulls are accumulated | D | 2023/01/11 | 2023/01/11 |
| /   | F04 | Player keeping random dice at their turn and takes a maximum of 3 turns| D | 2023/01/11 | 2023/01/11 |
| /   | F05 | Score points: coins and diamonds | D | 2023/01/11 | 2023/01/11 |
| /   | F06 | Add players  | D | 2023/01/11 | 2023/01/11 |
| /   | F07 | Add Turns  | D | 2023/01/11 | 2023/01/11 |
| /   | F08 | Repeat turns until game ends  | D | 2023/01/11 | 2023/01/11 |
| /   | F09 | Add second player  | D | 2023/01/11 | 2023/01/11 |
| /   | F10 | Store wins  | D | 2023/01/11 | 2023/01/11 |
| /   | F11 | Calculate win%  | D | 2023/01/11 | 2023/01/11 |
| /   | F12 | Run 42 times  | D | 2023/01/11 | 2023/01/11 |
| /   | F13 | Handle Ties  | D | 2023/01/11 | 2023/01/11 |
| x   | F14 | Multi/card Combo scoring  | D | 2023/01/16 | 2023/01/16 |
| x   | F15 | Combo Strategy  | D | 2023/01/16 | 2023/01/16 |
| x   | F16 | Toggle scoring with args  | P | 2023/01/16 |  |
| x   | F17 | "nop" cards  | D | 2023/01/17 | 2023/01/17 |
| x   | F18 | draw cards  | D | 2023/01/17 | 2023/01/17 |
| x   | F19 | Sea battle card  | D | 2023/01/17 | 2023/01/17 |
| x   | F20 | battle strategy  | D | 2023/01/17 | 2023/01/17 |
| x   | F21 | Monkey card  | D | 2023/01/18 | 2023/01/18 |
| x   | F22 | Gold card  | D | 2023/01/18 | 2023/01/18 |
| x   | F23 | Diamond card  | D | 2023/01/18 | 2023/01/18 |
| x   | F25 | Skull  card  | D | 2023/01/18 | 2023/01/18 |
| x   | F24 | Captain card  | D | 2023/01/18 | 2023/01/18 |
| x   | F25 | Sorceress  card  | D | 2023/01/18 | 2023/01/18 |
| x   | F26 | Island of skulls  | D | 2023/01/21 | 2023/01/21 |
| x   | F27 | Change number of games Via command line  | P |  |  |
| x   | F28 | Full Chest Mechanic  | D | 2023/01/21 | 2023/01/21 |
| ... | ... | ... |

