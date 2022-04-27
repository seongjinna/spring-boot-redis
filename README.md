# spring-boot-redis
- 스프링 부트에서 Redis 사용하기
- Quartz Scheduler 사용하기

## Getting Started
- Java version : JDK 8
- Spring Boot : 2.6.6

### Dependencies
- Spring Web
- Spring Data Redis
- Lettuce : Redis Java Client
- Embedded Redis : 로컬 개발용 Redis
- Spring Quartz : 배치 스케쥴러

### Test
- ./gradlew test
- 테스트 결과 : ./build/reports/tests/test/packages/com.example.demo.html
- http://localhost:8081/swagger-ui/index.html

### Lessons learned
- 로컬/개발/테스트/운영 등 다양한 환경에서 Redis를 함께 사용할 수 있도록 환경 구성
- Quartz Scheduler 이용하여 일정 간격으로 API 호출하여 Redis 저장
- Swagger 적용