# Transition
- Flow 내 스텝의 조건부 전환을 정의
- 잡의 API 설정에서 on(String pattern) 메소드를 호출하면 TransitionBuilder 가 반환되어 Transition Flow 를 구성할 수 있음
- 스텝의 종료상태(ExitStatus)가 어떤 pattern 과도 매칭되지 않으면 스프링 배치에서 예외를 발생시키고 잡은 실패
#### API
- **on**(String pattern)
  - 스텝의 실행 결과로 돌려받는 종료상태(ExitStatus)와 매칭하는 패턴 스키마. BatchStatus 와 매칭하는 것 X
  - pattern 과 ExitStatus 가 매칭이 되면 다음으로 실행할 스텝을 지정할 수 있다
- **to**()
  - 다음으로 실행할 단계를 지정
- **from**()
  - 이전 단계에서 정의한 Transition 을 새롭게 추가 정의
#### 잡을 중단하거나 종료하는 Transition API
- Flow 가 실행되면 `FlowExecutionStatus` 에 상태값이 저장되고 최종적으로 잡의 BatchStatus 와 ExitStatus 에 반영된다
- Step 의 BatchStatus 및 ExitStatus 에는 아무런 영향을 주지 않고 잡의 상태만을 변경
- **stop()**
  - `FlowExecutionStatus` 가 **STOPPED** 상태로 종료되는 transition
  - 잡의 BatchStatus 와 ExitStatus 가 **STOPPED** 으로 종료됨
- **fail()**
  - `FlowExecutionStatus` 가 **FAILED** 상태로 종료되는 transition
  - 잡의 BatchStatus 와 ExitStatus 가 **FAILED** 으로 종료됨
- **end()**
  - `FlowExecutionStatus` 가 **COMPLETED** 상태로 종료되는 transition
  - 스텝의 ExitStatus 가 FAILED 이더라도 잡의 BatchStatus 가 **COMPLETED** 로 종료하도록 가능. 이때 잡의 재시작은 불가능
- **stopAndRestart(Step or Flow or JobExecutionDecider)**
  - stop() transition 과 기본 흐름은 동일
  - 특정 스텝에서 작업을 중단하도록 설정하면 중단 이전의 스텝만 COMPLETED 로 저장되고 이후의 스텝은 실행되지 않고 STOPPED 상태로 잡을 종료
  - 잡이 다시 실행됐을 때 실행해야할 스텝을 restart 인자로 넘기면 이전에 COMPLETED 로 저장된 스텝은 건너뛰고 중단 이후 스텝부터 시작
```java
Job batchJob() {
    return jobBuilderFactory.get("batchJob")
        .start(Flow)
        .next(Step or Flow or JobExecutionDecider)
        .on(String pattern) // TransitionBuilder 반환
        .to(Step or Flow or JobExecutionDecider)
        .stop() / fail() /end() / stopAndRestart(Step or Flow or JobExecutionDecider)
        .end()
        .build();
}
```
