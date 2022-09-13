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

* [JobInstance] 
  * Job이 실행될 때 생성되는 Job의 논리적 실행 단위 객체, 고유하게 식별 가능한 작업 실행을 나타낸다.
  * Job의 설정과 구성 동일 but Job이 실행되는 시점에 처리하는 내용은 다르다 따라서 Job의 실행을 구분해야 한다.
    * ex) 하루에 한 번씩 배치 Job이 실행된다면 매일 실행되는 각각의 Job을 JobInstance 로 표현한다.

* JobInstance 생성 및 실행
  * 처음 시작하는 Job + JobParameter 일 경우 새로운 JobInstance 생성
  * 이전과 동일한 Job + JobParameter 으로 실행 할 경우 이미 존재하는 JobInstance return
    * 내부적으로 JobName + jobKey (jobParameters 의 해시 값) 를 가지고 JobInstance 객체를 얻음

* Job(1) : JobInstance(N) 관계


### 2.3 JobParameter
* [JobParameter]
  * Job을 실행할 때 함께 포함되는 사용되는 파라미터를 가진 도메인 객체
  * 하나의 Job에 존재할 수 있는 여러개의 JobInstance를 구분하기 위한 용도
  * JobParameters와 JobInstance 는 1:1 관계

* 새성 및 바인딩
  * 어플리케이션 실행 시 주입
    * java -jar LogBatch.jar requestData=20220827
  * 코드로 생성
    * JobParameterBuilder, DefaultJobParametersConverter
  * SpEL 이용
    * @Value("#{jobParameter[requestDate]}"), @JobScope, @StepScope 선언 필수
  

※ Test :: java -jar spring-batch-0.0.1-SNAPSHOT.jar 'name=user1 seq(long)=2L date(date)=2021/01/01 age(double)=16.5'



### 2.4 JobExecution

* [JobExecution]
  * JobInstance 에 대한 한번의 시도를 의미하는 객체로서 Job 실행 중에 발생한 정보들을 저장하는 객체
    * 시작시간, 종료시간, 상태(시작됨, 완료, 실패), 종료상태의 속성을 가짐)

  * JobInstance 와의 관계 
    * JobExecution은 'FAILED' 또는 'COMPLETED‘  등의 Job의 실행 결과 상태를 가지고 있음 
    * JobExecution 의 실행 상태 결과가 'COMPLETED’ 면 JobInstance 실행이 완료된 것으로 간주해서 재 실행이 불가함 
    * JobExecution 의 실행 상태 결과가 'FAILED’ 면 JobInstance 실행이 완료되지 않은 것으로 간주해서 재실행이 가능함 
      * JobParameter 가 동일한 값으로 Job 을 실행할지라도 JobInstance 를 계속 실행할 수 있음 
    * JobExecution 의 실행 상태 결과가 'COMPLETED’ 될 때까지 하나의 JobInstance 내에서 여러 번의 시도가 생길 수 있음



### 2.5 Step

* [Step]
  * Batch job을 구성하는 독립적인 하나의 단계로서 실제 배치 처리를 정의하고 컨트롤하는 데 필요한 모든 정보를 가지고 있는 도메인 객체
  * 단순한 단일 태스크 뿐 아니라 입력과 처리 그리고 출력과 관련된 복잡한 비즈니스 로직을 포함하는 모든 설정들을 담고 있다.
  * 배치작업을 어떻게 구성하고 실행할 것인지 Job 의 세부 작업을 Task 기반으로 설정하고 명세해 놓은 객체
  * 모든 Job은 하나 이상의 Step으로 구성됨


* TaskletStep 
  * 가장 기본이 되는 클래스로서 Tasklet 타입의 구현체들을 제어한다
* PartitionStep 
  * 멀티 스레드 방식으로 Step 을 여러 개로 분리해서 실행한다
* JobStep 
  * Step 내에서 Job 을 실행하도록 한다
* FlowStep 
  * Step 내에서 Flow 를 실행하도록 한다




### 2.6 StepExecution

### 2.7 ExecutionContext

### 2.8 JobRepository

### 2.9 JobLauncher




