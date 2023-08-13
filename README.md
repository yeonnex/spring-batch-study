# Spring Batch 시작하기
### 0. @EnableBatchProcessing 으로 스프링 배치 활성화
- 총 4개의 설정클래스(@Configuration) 실행시키며 스프링 배치의 초기화가 이루어진다
  - 1. @EnableBatchProcessing
  - 2. SimpleBatchConfiguration
  - 3. BatchConfigurerConfiguration
    - 1. BasicBatchConfigurer
    - 2. JpaBatchConfigurer
  - 4. BatchAutoConfiguration
- 스프링부트배치의 자동설정이 실행됨으로 빈으로 등록한 모든 Job 을 검색하여 초기화와 동시에 Job 을 수행한다
### 1. @Configuration 선언
- 배치 Job 을 정의하고 빈으로 설정한다
### 2. JobBuilderFactory 
- Job 을 쉽게 생성할 수 있게 도와주는 빌더 클래스이다
- `new Job()`  이 아닌, 빌더 팩토리 클래스를 사용해 쉽게 Job 을 생성한다
### 3. StepBuilderFactory
- Step 을 쉽게 생성할 수 있게 도와주는 빌더 클래스이다
- `new Step()` 이 아닌, 빌더 팩토리 클래스를 사용해 쉽게 Step 을 생성한다
### 4. Job
- JobBuilderFactory.get('<i>생성할 잡의 이름</i>') 을 통해 Job 을 생성한다
  - ```java
    jobBuilderFactory.get('helloJob'); // helloJob 이라는 이름의 Job 이 생성되었다
    ```
### 5. Step
- StepBuilderFactory.get('<i>생성할 스텝의 이름</i>') 을 통해 Step 을 생성한다
  - ```java
    stepBuilderFactory.get('helloStep'); // helloStep 이라는 이름의 Step 이 생성되었다
    ```
### 6. Tasklet
- Step 안에서 단일 태스크로 실행되는 로직을 구현한다
  - ```java
    stepBuilderFactory.get('helloStep')
            .tasklet((contribution, chunkContext) => {
                System.out.println("안녕, 스프링 배치");
        })
    ```
### 정리:  Job 구동 -> Step 을 실행 -> Tasklet 을 실행
**Job** : 일, 일감 </br>
**Step** : 일의 항목, 단계 </br>
**Tasklet** : 실제 작업 내용 </br>