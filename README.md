# java-chess

## 규칙

- 게임은 두 명(흑과 백)의 플레이어로 나누어 진행된다. 
- 각 플레이어의 턴이 한 번 진행된 후, 상대의 턴으로 변경된다. 
- 상대방의 왕을 잡으면 게임에서 승리하며, 게임이 종료된다.

- 레퍼런스 : [체스 나무위키](https://namu.wiki/w/%EC%B2%B4%EC%8A%A4)
    - 추가규칙 (프로모션, 캐슬링, 앙파상) 은 우선순위를 미룬다.

### 기물

- 각 기물은 서로 다른 움직임과 점수를 가진다. (점수는 체스판에 남아있는 기물의 점수를 계산한다.)
  - 킹 
    - 모든 방향으로 1칸씩 이동할 수 있다. 
    - 0점 (킹이 잡히면 게임이 끝나기 때문에 점수를 가지지 않는다.)
  - 퀸 
     - 모든 방향 (직선 방향 + 대각선 방향)으로 거리에 상관 없이 움직일 수 있다.
     - 이동 방향에 기물이 존재하면 움직일 수 없다.
    - 9점
  - 룩 
    - 직선 방향 (상, 하, 좌, 우)로 거리에 상관 없이 움직일 수 있다.
    - 이동 방향에 기물이 존재하면 움직일 수 없다.
    - 5점
  - 비숍
    - 모든 대각선 방향으로 거리에 상관 없이 움직일 수 있다. 
    - 이동 방향에 기물이 존재하면 움직일 수 없다.
    - 3점
  - 나이트
    - 직선 방향으로 한칸, 해당 방향의 양 대각선 방향 중 한 방향으로 한 칸 움직일 수 있다.
    - 이동 방향에 있는 기물을 뛰어넘을 수 있다.
    - 2.5점
  - 폰
    - 진행방향 (흑 -> 아래, 백 -> 위)으로 한 칸씩 이동할 수 있다.
    - 첫 움직임에 한해 진행 방향으로 두 칸 움직일 수 있다. 
    - 상대 기물이 대각선 방향에 위치한다면 대각선 방향으로 한 칸 이동할 수 있다.
    - 1점 (단, 같은 세로줄에 같은 색의 폰이 존재하는 경우 0.5점으로 계산한다.)

## 프로그램 

### 명령어

- "START"
  - 체스 게임을 시작한다.
- "MOVE [SOURCE] [TARGET]"
  - SOURCE 위치의 기물을 TARGET 위치로 이동한다. 
- "END"
  - 체스게임을 종료한다.
- "STATUS"
  - 현재 자신의 턴인 플레이어의 점수를 출력한다. 
- "SAVE"
  - 게임의 현재 상태를 데이터베이스에 저장한다.

### 실행 흐름

1. "start"를 입력하면 체스 게임을 시작한다.
    - 대소문자를 무시한다.
2. 기물을 이동시킬 수 있다
    - "move source위치 target위치" 를 입력하면 source 위치의 기물을 target 위치로 이동시킨다.
        - 위치 : A~H 사이의 알파벳과 1\~8 사이의 숫자 조합으로 입력한다. (예시 : b2, H8...)
    - [Exception] source 위치에 본인의 기물이 존재하지 않으면 예외가 발생한다.
    - [Exception] 기물이 target 위치로 이동할 수 없으면 예외가 발생한다.
3. "end"를 입력하면 체스 게임을 종료한다.

### 게임 시작시 초기 세팅

```
RNBQKBNR
PPPPPPPP
........
........
........
........
pppppppp
rnbqkbnr
```
