# 기능 요구사항

- 콘솔 UI에서 체스 게임을 할 수 있는 기능을 구현한다.
- 1단계는 체스 게임을 할 수 있는 체스판을 초기화한다.
- 체스판에서 말의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.

- 체스 말의 이동 규칙을 찾아보고 체스 말이 이동할 수 있도록 구현한다.
- move source위치 target위치을 실행해 이동한다.

# 기능 구현 목록

## 게임 명령을 입력하는 기능

- [x] `> 체스 게임을 시작합니다. > 게임 시작 : start > 게임 종료 : end`를 출력한다.
- [x] `start`, `end`를 입력한다.

## 체스 게임을 초기화하는 기능

- [x] 체스판을 초기화한다.
- [x] 기물을 배치한다.
- [x] 초기화된 체스판을 출력한다.

## 체스판에서 기물을 이동하는 기능

- [x] `게임 이동 : move source위치 target위치 - 예. move b2 b3`를 출력한다.
- [x] `move b2 b3`와 같이 입력한다.
    - [x] 형식에 맞지 않는 입력을 예외 처리한다.
- [x] 기물을 이동한다.
    - [x] 범위를 벗어난 입력인 경우 예외 처리한다.
    - [x] source 위치에 기물이 없는 경우 예외 처리한다.
    - [x] target 위치에 같은 진영의 기물이 있는 경우 예외 처리한다.
    - [x] source와 target 위치가 일치하는 경우 예외 처리한다.
- [x] 이동 후의 체스판의 상태를 출력한다.

## 체스판 점수 계산
- [x] King이 잡히는 경우 경기가 끝난다.
- [x] 현재 남아 있는 기물에 대한 점수를 구한다.
  - [x] Queen: 9점
  - [x] Rook: 5점
  - [x] Bishop: 3점
  - [x] Knight: 2.5점
  - [x] Pawn: 1점
    - [x] 같은 세로줄에 같은 색의 Pawn이 있다면, 0.5점으로 계산한다.

## 점수판 출력 
- [x] 각 진영의 점수를 출력한다
- [x] 어느 진영이 이겼는지 결과를 출력한다.

## 체스 게임 저장
### Command에 따른 체스게임 실행
- start
  - 진행중인 게임이 있다면, 불러온다.
  - 진행중인 게임이 없다면, 새로 만든다.
- move 
  - 기물의 위치를 업데이트한다.
- end 
  - 기존 게임을 없앤다.
  - 
### DB 실행하는 방법
- docekr-compose.yml 파일이 있는 경로에서, docker 명령어로 Server를 실행
- 테이블 생성
```
Docker 실행하기
docker-compose -p chess up -d
```
```sql
create table chess_board
(
    id            int auto_increment
        primary key,
    chess_game_id int         null,
    board_file    varchar(20) null,
    board_rank    varchar(20) null,
    piece_type    varchar(20) not null,
    piece_color   varchar(20) not null
);

create table chess_game
(
    id          int auto_increment
        primary key,
    game_status varchar(20) not null,
    color       varchar(20) not null
);


```
