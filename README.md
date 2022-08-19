# [Spring Batch Study]


## Part1 Spring Batch Start 

### 1.1 Hello Spring Batch 시작하기

1. @Configuration 선언
    - 하나의 배치 Job 을 정의하고 빈 설정
2. JobBuilderFactory
    - Job을 생성하는 빌더 팩토리
3. StepBuilderFactory
    - Step 을 생성하는 빌더 팩토리
4. Job
    - helloJob 이름으로 Job 생성
5. Step
    - helloStep 이름으로 Step 생성
6. tasklet
    - Step 안에서 단일 태스크로 수행되는 로직 구현
7. Job 구동 -> Step 을 실행 -> Tasklet 을 실행

JOB - 일, 일감 <br>
STEP - 일의 항목 <br> 
단계 TASKLET - 작업 내용


<b>정리 : 하나의 Job 은 여러개의 Step 으로 구성되어 있다. 하나의 Step 은 Tasklet(작업 내용) 을 가지고 있다.</b>

### 2.1 Batch Config

@EnableBatchProcessing
- 스프링 배치가 작동하기 위해 선언해야 하는 어노테이션
- 총 4개의 설정 클래스를 실행시키며 스프링 배치의 모든 초기화 및 실행 구성이 이루어진다.
- 스프링 부트 배치의 자동 설정 클래스가 실행됨으로 빈으로 등록된 모든 Job을 검색해서 초기화와 동시에 Job을 수행하도록 구성됨

1. BatchAutoConfiguration
- 스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스
- Job을 수행하는 JobLauncherApplicationRunner 빈을 생성

2. SimpleBatchConfiguration
- JobBuilderFactory 와 StepBuilderFactory 생성
- 스프링 배치의 주요 구성 요소 생성 - 프록시 객체로 생성됨

3. BatchConfigurerConfiguration
- BasicBatchConfigurer
   * SimpleBatchConfiguration 에서 생성한 프폭시 객체의 실제 대상 객첼를 생성하는 설정 클래스
   * 빈으로 의종성 주입 받아서 주요 객체들을 참조해서 사용할 수 있다.

- JpaBatchConfigurer
   * JPA 관련 객체를 생성하는 설정 클래스

## Part2 스프링 배치 도메인 이해

### 2.1 Job

#### [그래서 Job 이 뭔데?]
* Job 이란 배치 계층 구조에서 가장 상위에 있는 개념으로 하나의 배치작업 자체를 의미함
    * ex) API 서버의 접속 로그 데이터를 통계 서버로 옮기는 배치 인 Job 자체를 의미한다.
* Job Configuration 을 통해 생성되는 객체 단위로서 배치작업을 어떻게 구성하고 실행 한 것인지 전체적으로 설명하고 명세해 놓은 객체
    * 즉, 하나의 '일' 이다.
* 배치 Job 을 구성하기 위한 최상의 인터페이스이며 Spring Batch가 기본 구현체를 제공한다.

* 하나의 Job 은 여러개의 Step 으로 구성 되어 있으며, 반드시 한개 이상의 Step 으로 구성해야 한다.


#### [구현체]

* SimpleJob
    * 순차적으로 Step을 실행시키는 Job
* FlowJob
    * Job에 흐름에 있어서 step1 이후 step2 를 실행 시킬지 step3 를 실행 시킬지 특정 조건과 흐름에 딷라서 Step을 구성하여 실행시키는 Job
    * Flow 객체를 실행시켜 작업을 진행한다.




### 2.2 JobInstance


### 2.3 JobParameter


### 2.4 JobExecution

### 2.5 Step

### 2.6 StepExecution

### 2.7 ExecutionContext

### 2.8 JobRepository

### 2.9 JobLauncher




