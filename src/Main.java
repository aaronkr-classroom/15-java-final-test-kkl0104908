import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JTextArea display;
    private double operand1;
    private String operator;

    public Main() {
        setTitle("간단한 계산기");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 변수 초기화
        operand1 = 0.0;
        operator = "";

        // 화면 표시용 텍스트 영역 생성
        display = new JTextArea(2, 10);
        display.setEditable(false); // 수정 불가능하도록 설정
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // 메뉴 바와 메뉴 생성
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // 파일(File) 메뉴
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);

        // New 메뉴 항목의 ActionListener
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDisplay(); // 화면 초기화 메서드 호출
            }
        });

        // Open 메뉴 항목의 ActionListener
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this,
                        "열기 메뉴 클릭됨.", "열기", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 편집(Edit) 메뉴
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        // Copy 메뉴 항목의 ActionListener
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.copy(); // JTextArea의 복사 기능 호출
            }
        });

        // Paste 메뉴 항목의 ActionListener
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.paste(); // JTextArea의 붙여넣기 기능 호출
            }
        });

        // 도움말(Help) 메뉴
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem aboutMenuItem = new JMenuItem("About");

        helpMenu.add(aboutMenuItem);

        // About 메뉴 항목의 ActionListener
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this,
                        "간단한 계산기\n\n" +
                                "기본 계산기 GUI 애플리케이션입니다.\n" +
                                "Created by [Your Name]", "정보", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 숫자 및 연산 버튼 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        // 컴포넌트를 콘텐츠 패널에 추가
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(display, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // 화면 초기화 메서드
    private void clearDisplay() {
        display.setText("");
        operand1 = 0.0;
        operator = "";
    }

    // 숫자 및 연산 버튼의 ActionListener 구현
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "+":
                case "-":
                case "*":
                case "/":
                    operator = command;
                    operand1 = Double.parseDouble(display.getText());
                    display.setText("");
                    break;
                case "=":
                    double operand2 = Double.parseDouble(display.getText());
                    double result = performOperation(operand1, operand2, operator);
                    display.setText(String.valueOf(result));
                    operator = "";
                    operand1 = result;
                    break;
                default:
                    display.append(command);
                    break;
            }
        }
    }

    // 연산 수행 메서드
    private double performOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    JOptionPane.showMessageDialog(Main.this, "Error: Division by zero");
                    return 0.0; // 0으로 나누는 오류 처리
                }
                return operand1 / operand2;
            default:
                return 0.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
