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

