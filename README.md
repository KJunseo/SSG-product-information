# 상품정보 제공 API 구축

## Github
- https://github.com/KJunseo/SSG-product-information

## 🛠 기술 스택
- 언어: Java 11
- 프레임워크: Spring Boot 2.7.0, Spring MVC, Spring rest docs
- Database: h2, Spring Data JPA
- 테스트: Junit5, REST Assured

## 📚 기능
- 사용자
  - 사용자 생성
  - 사용자 탈퇴
- 상품
  - 상품 생성
  - 상품 삭제
  - 사용자 별 구매 가능 상품 목록 조회
  - 상품 별 프로모션 정보 조회
- 프로모션
  - 프로모션 생성
  - 프로모션 삭제
  
## ▶️ 실행 방법
`./gradlew build`

## 📝 API 문서
- 프로젝트에서 보기
  - [link](./src/docs/asciidoc/api.adoc)
  
- 브라우저에서 보기
  - `./gradlew build` 
  - `cd build/libs`
  - `java -jar product_information-0.0.1-SNAPSHOT.jar `
  - http://localhost:8080/docs/api.html
