# JavaPro
프로젝트 개요

이 프로젝트는 카페 키오스크(Cafe Kiosk System) 를 Java Swing 기반으로 구현한 프로그램입니다.
사용자가 메뉴를 선택하면 장바구니에 담기고, 총액을 계산하여 보여주며,
오늘 하루 동안의 주문 내역을 확인할 수 있는 기능 을 추가로 제공합니다.

개발 언어: Java

GUI 라이브러리: Swing

개발 도구: IntelliJ IDEA (또는 Eclipse 등 Java IDE)

2. 주요 기능(기능 설명)
2-1. 메뉴 선택 및 장바구니

메인 화면 왼쪽에 4개의 메뉴 버튼이 배치됩니다.

Americano (2500₮)

Latte (3500₮)

Espresso (2000₮)

Cheesecake (4500₮)

사용자가 메뉴 버튼을 클릭하면 해당 메뉴가 장바구니(ArrayList) 에 추가됩니다.

오른쪽 상단의 주문 내역 영역에 선택된 메뉴와 가격이 한 줄씩 표시됩니다.

하단의 총액: 라벨에 현재 장바구니의 총 금액이 실시간으로 갱신됩니다.

2-2. 주문하기 버튼

주문하기 버튼 클릭 시:

장바구니가 비어 있으면
→ 장바구니가 비어 있습니다! 라는 경고 메시지를 띄웁니다.

장바구니에 메뉴가 있으면

현재 장바구니 내용을 “오늘 주문 내역” 리스트에 저장합니다.

주문이 완료되었습니다! 총액: XXX₮ 팝업을 보여줍니다.

장바구니를 비우고 화면을 초기화합니다.

2-3. 비우기 버튼

비우기 버튼 클릭 시:

현재 장바구니에 담겨 있는 메뉴 리스트를 모두 삭제합니다.

총액을 0으로 초기화합니다.

오른쪽 주문 내역 표시도 새로 갱신됩니다.

2-4. 오늘 주문 내역 버튼

오늘 주문 내역 버튼 클릭 시:

오늘 프로그램을 실행한 동안 주문하기 버튼으로 확정된 모든 주문 목록 을 팝업으로 보여줍니다.

각 주문은 다음과 같은 형식으로 저장됩니다.

1번째 주문: Americano(2500₮), Latte(3500₮), 총액: 6000₮

만약 아직 확정된 주문이 없다면
→ 오늘 등록된 주문이 없습니다. 라는 메시지를 보여줍니다.

3. 프로그램 구조(클래스 설명)
3-1. Main 클래스 (Main.java)
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            KioskFrame frame = new KioskFrame();
            frame.setVisible(true);
        });
    }
}


Java 프로그램의 시작점.

SwingUtilities.invokeLater() 를 사용하여
GUI 생성 코드를 Event Dispatch Thread(EDT) 에서 안전하게 실행합니다.

KioskFrame 객체를 생성하고 setVisible(true) 로 창을 화면에 띄웁니다.

3-2. MenuItem 클래스 (MenuItem.java)
public class MenuItem {
    private String name; // 메뉴 이름
    private int price;   // 가격

    public MenuItem(String name, int price) { ... }

    public String getName() { ... }
    public int getPrice() { ... }

    @Override
    public String toString() {
        return name + " (" + price + "₮)";
    }
}


하나의 메뉴(예: Americano, Latte)를 표현하는 데이터 모델 클래스 입니다.

필드:

name : 메뉴 이름

price : 메뉴 가격

메서드:

getName(), getPrice() : 메뉴 정보 조회

toString() : 메뉴를 "Americano (2500₮)" 형태의 문자열로 반환

3-3. KioskFrame 클래스 (KioskFrame.java)

JFrame 을 상속받아 실제 키오스크 화면을 구성하고 로직을 처리하는 핵심 클래스입니다.

(1) 주요 UI 컴포넌트

menuPanel : 왼쪽의 메뉴 버튼 4개가 배치되는 패널 (GridLayout 2×2)

orderList : 오른쪽 상단의 주문 내역을 보여주는 JTextArea

totalLabel : 하단의 총액(총액: XXX₮)을 보여주는 JLabel

orderButton : 주문 확정 버튼 (주문하기)

clearButton : 장바구니 비우기 버튼 (비우기)

historyButton : 오늘 주문 내역 확인 버튼 (오늘 주문 내역)

(2) 데이터 구조

ArrayList<MenuItem> cart
→ 현재 장바구니에 담겨 있는 메뉴 목록

int totalPrice
→ 장바구니의 총 금액

ArrayList<String> orderHistory
→ 오늘 확정된 주문들을 문자열로 저장하는 리스트

int orderCount
→ 몇 번째 주문인지 카운트하는 변수 (1번째, 2번째 …)

(3) 주요 메서드

addMenuButton(String name, int price)
→ 메뉴 버튼을 생성하고, 클릭 시 해당 메뉴를 cart에 추가하도록 ActionListener 설정

addToCart(MenuItem item)
→ 장바구니에 메뉴 추가 + 총액 갱신 + 화면 업데이트

clearCart()
→ 장바구니와 총액을 초기화하고 화면을 새로 그림

updateDisplay()
→ orderList 와 totalLabel 을 현재 cart와 totalPrice 값에 맞게 다시 출력

saveCurrentOrderToHistory()
→ 현재 장바구니 정보를 한 줄 문자열 로 만들어 orderHistory 에 저장

showOrderHistory()
→ orderHistory 내용을 모아서 팝업(JOptionPane) 으로 출력

4. 실행 방법

JDK 설치

JDK 8 이상 설치 (예: Temurin, Oracle JDK 등)

프로젝트 열기

IntelliJ IDEA 또는 Eclipse에서 Java 프로젝트로 Main.java, KioskFrame.java, MenuItem.java 파일을 포함한 프로젝트를 연다.

Main 클래스 실행

Main.java 파일을 열고
→ Run 'Main.main()' 으로 프로그램 실행.

프로그램 사용 방법

왼쪽의 메뉴 버튼 중 원하는 음료/디저트를 클릭하여 장바구니에 담는다.

오른쪽 상단 주문 내역 에 담긴 메뉴와 금액을 확인한다.

하단의 주문하기 버튼:

장바구니가 비어 있으면 경고 메시지

메뉴가 있으면 주문 완료 메시지와 함께 오늘 주문 내역에 저장

비우기 버튼:

현재 장바구니와 총액을 초기화

오늘 주문 내역 버튼:

오늘 진행된 모든 주문 목록을 팝업으로 확인 가능

5. 개선 및 확장 가능성

주문 내역을 텍스트 파일이나 DB에 저장하여 프로그램을 다시 실행해도 기록이 남도록 확장 가능

관리자 페이지를 만들어

메뉴 추가/수정/삭제 기능

하루 매출 합계, 인기 메뉴 통계 등 표시 가능

로그인/회원 기능을 추가하여

포인트 적립, 단골 손님 관리 등으로 확장 가능
