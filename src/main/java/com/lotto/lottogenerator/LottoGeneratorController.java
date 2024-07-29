package com.lotto.lottogenerator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.*;


public class LottoGeneratorController {

    @FXML
    private Label result1,result2,result3,result4,result5,result6;
    @FXML
    private Button slot1Button, slot2Button, slot3Button, slot4Button, slot5Button, slot6Button;
    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45;
    @FXML
    private ToggleButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45;
    @FXML
    private Button generateButton, saveButton, resetButton;
    @FXML
    private Label currentSlotLabel;
    @FXML
    private TextArea displayArea;
    @FXML
    public static final Random random = new Random();
    @FXML
    private List<Integer> slot1List,slot2List,slot3List,slot4List,slot5List,slot6List;
    private Label[] resultLabels;
    private Button[] slotButtons;
    private Button[] selectButtons;
    private ToggleButton[] resultButtons;
    private Set<String> savedCombinations = new HashSet<>();
    private int[] selectedNumbers = new int[45];
    private static List<Integer> currentSlotList;
    private static int currentSlotNumber = 0;
    private static String savedDisplayAreaText;
    private static final String SAVE_FILE = "lotto_save.dat";


    @FXML
    public void initialize() {
        slotButtons = new Button[]{slot1Button, slot2Button, slot3Button, slot4Button, slot5Button, slot6Button};
        selectButtons = new Button[]{ b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45};
        resultButtons = new ToggleButton[]{r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45};
        resultLabels = new Label[]{result1,result2,result3,result4,result5,result6};
        currentSlotList = new ArrayList<>();
        slot1List = new ArrayList<>(); slot2List = new ArrayList<>(); slot3List = new ArrayList<>();
        slot4List = new ArrayList<>(); slot5List = new ArrayList<>(); slot6List = new ArrayList<>();

        // 슬롯 기본값 = 슬롯1
        currentSlotList = slot1List;


        //로또 번호 클릭시 슬롯에 로또 번호가 들어가는 기능
        for (int i = 0; i < selectButtons.length; i++) {
            final int index = i;
            selectButtons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleSelectButton(index));
        }

        //임의의 슬롯 버튼 클릭 시 클릭한 슬롯이 현재 슬롯으로 지정됨.
        for (int i = 0; i < slotButtons.length; i++){
            final int slotIndex = i;
            slotButtons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, e-> handleCurrentSlot(slotIndex));
        }

        generateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleGenerateButton());
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleSaveButton());
        resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleResetButton());

        //저장된 상태 파일이 있으면 불러옴.
        loadState();
    }

    private void handleCurrentSlot(int slotIndex){

        currentSlotNumber = slotIndex;

        switch(slotIndex){
            case 0 -> currentSlotList = slot1List;
            case 1 -> currentSlotList = slot2List;
            case 2 -> currentSlotList = slot3List;
            case 3 -> currentSlotList = slot4List;
            case 4 -> currentSlotList = slot5List;
            case 5 -> currentSlotList = slot6List;
        }

        //현재 슬롯 표시
        currentSlotLabel.setText("현재 선택된 슬롯 : " + (slotIndex + 1));

        //슬롯을 바꾸었을 때,
        //1. 슬롯을 바꾸면 선택된 로또 배열 전체를 0으로 다시 초기화
        for( int i = 0 ; i < selectedNumbers.length ; i++) {
            selectedNumbers[i] = 0;
        }
        //Arrays.fill(selectedNumbers,0) 이렇게 써도됨.

        //2. 슬롯을 바꾸면 선택한 슬롯의 데이터로 선택된 로또번호를 다시 초기화함.
        for (Integer i : currentSlotList){
            selectedNumbers[i-1] = 1;
        }

    }

    private void handleSelectButton(int index) {
        //로또 번호 아직 선택 되지 않았을 때,
        if (selectedNumbers[index] == 0) {
//                if( currentSlotList.size() < 6) { 크기 제한
            selectedNumbers[index] = 1;
            currentSlotList.add(index + 1);
            Collections.sort(currentSlotList);
            String removedString = currentSlotList.toString().substring(1, currentSlotList.toString().length() - 1);
            slotButtons[currentSlotNumber].setText(removedString);
//                }else{ // 로또 번호가 6개가 넘을 시 Alert
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("오류");
//                    alert.setHeaderText("로또 번호가 6자리를 초과했습니다.");
//                    alert.showAndWait();
//                }
            // 로또 번호가 이미 선택되었을 때,
        } else if (selectedNumbers[index] == 1) {
            selectedNumbers[index] = 0;

            currentSlotList.remove(Integer.valueOf(index + 1));
            Collections.sort(currentSlotList);

            String removedString = currentSlotList.toString().substring(1, currentSlotList.toString().length() - 1);
            slotButtons[currentSlotNumber].setText(removedString);
        }
    }

    private void handleGenerateButton() {
        if (!slot1List.isEmpty() && !slot2List.isEmpty() && !slot3List.isEmpty() && !slot4List.isEmpty() && !slot5List.isEmpty() && !slot6List.isEmpty()) {

            boolean uniqueCombination = false;

            while (!uniqueCombination) {
                List<Integer> generatedNumbers = new ArrayList<>();

                //각 슬롯에서 뽑은 숫자보다 다음 슬롯에서 뽑은 번호가 반드시 커야함.
                //Slot1에서 뽑은 숫자보다 큰 Slot2의 랜덤 번호 뽑기
                int randomIndex1 = random.nextInt(slot1List.size());
                Integer number1 = slot1List.get(randomIndex1);
                generatedNumbers.add(number1);


                List<Integer> filteredSlot2List = filterSlotList(slot2List, number1);
                if (filteredSlot2List.isEmpty()) {
                    showAlertForLackOfNumber();
                    return;
                }

                //Slot2에서 뽑은 숫자보다 큰 Slot3의 랜덤 번호 뽑기
                int randomIndex2 = random.nextInt(filteredSlot2List.size());
                int number2 = filteredSlot2List.get(randomIndex2);
                generatedNumbers.add(number2);


                List<Integer> filteredSlot3List = filterSlotList(slot3List, number2);

                if (filteredSlot3List.isEmpty()) {
                    showAlertForLackOfNumber();
                    return;
                }

                //Slot3에서 뽑은 숫자보다 Slot4에서 뽑은 숫자가 더 큰 랜덤 번호 뽑기
                int randomIndex3 = random.nextInt(filteredSlot3List.size());
                int number3 = filteredSlot3List.get(randomIndex3);
                generatedNumbers.add(number3);


                List<Integer> filteredSlot4List = filterSlotList(slot4List, number3);
                if (filteredSlot4List.isEmpty()) {
                    showAlertForLackOfNumber();
                    return;
                }

                //Slot4에서 뽑은 숫자보다 Slot5에서 뽑은 숫자가 더 큰 랜덤 번호 뽑기
                int randomIndex4 = random.nextInt(filteredSlot4List.size());
                int number4 = filteredSlot4List.get(randomIndex4);
                generatedNumbers.add(number4);


                List<Integer> filteredSlot5List = filterSlotList(slot5List, number4);

                if (filteredSlot5List.isEmpty()) {
                    showAlertForLackOfNumber();
                    return;
                }

                //Slot5에서 뽑은 숫자보다 Slot6에서 뽑은 숫자가 더 큰 랜덤 번호 뽑기
                int randomIndex5 = random.nextInt(filteredSlot5List.size());
                int number5 = filteredSlot5List.get(randomIndex5);
                generatedNumbers.add(number5);


                List<Integer> filteredSlot6List = filterSlotList(slot6List, number5);

                if (filteredSlot6List.isEmpty()) {
                    showAlertForLackOfNumber();
                    return;
                }

                //Slot6에서 뽑은 숫자는 Slot5에서 뽑은 숫자보다 커야함.
                int randomIndex6 = random.nextInt(filteredSlot6List.size());
                int number6 = filteredSlot6List.get(randomIndex6);

                generatedNumbers.add(number6);

                String combination = generatedNumbers.toString();


                // 한번 저장된 조합은 다음 조합에서는 제외된다.
                if(!savedCombinations.contains(combination)){
                    uniqueCombination = true;
                    savedCombinations.add(combination);
                    displayArea.appendText("생성된 로또 번호 : " + combination + "\n");

                    result1.setText(String.valueOf(number1));
                    result2.setText(String.valueOf(number2));
                    result3.setText(String.valueOf(number3));
                    result4.setText(String.valueOf(number4));
                    result5.setText(String.valueOf(number5));
                    result6.setText(String.valueOf(number6));

                    //로또 번호가 로또 용지에 맞게 체크
                    lottoNumberToggleCheck();
                }
            }
        }else{
            showAlertForEmptySlot();
        }
    }

    private List<Integer> filterSlotList(List<Integer> slotList, int threshold){
        List<Integer> filteredList = new ArrayList<>();
        for (int num : slotList){
            if (num > threshold) {
                filteredList.add(num);
            }
        }
        return filteredList;
    }

    private static void showAlertForEmptySlot() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("오류");
        alert.setHeaderText("슬롯이 비어 있습니다.");
        alert.showAndWait();
    }

    private void showAlertForLackOfNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("오류");
        alert.setHeaderText("가능한 숫자가 부족합니다. \n 슬롯을 확인하세요.");
        alert.showAndWait();
    }


    @FXML
    private void handleSaveButton(){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))){
            //슬롯 저장
            oos.writeObject(slot1List);
            oos.writeObject(slot2List);
            oos.writeObject(slot3List);
            oos.writeObject(slot4List);
            oos.writeObject(slot5List);
            oos.writeObject(slot6List);

            //조합 저장
            oos.writeObject(savedCombinations);
            oos.writeInt(currentSlotNumber);

            //결과 버튼 저장
            for (Label resultLabel : resultLabels) {
                oos.writeObject(resultLabel.getText());
            }

            //로또 번호 생성 내역 저장
            oos.writeObject(displayArea.getText());

            displayArea.appendText("현재 상태를 저장하였습니다. \n");
        } catch (IOException e) {
            displayArea.appendText("현재 상태 저장에 실패하였습니다. \n");
            e.printStackTrace();
        }
    }

    private void handleResetButton(){
        slot1List.clear();
        slot2List.clear();
        slot3List.clear();
        slot4List.clear();
        slot5List.clear();
        slot6List.clear();
        savedCombinations.clear();
        currentSlotList.clear();
        currentSlotNumber = 0;

        currentSlotList = slot1List;

        for( int i = 0 ; i < selectedNumbers.length ; i++) {
            selectedNumbers[i] = 0;
        }
        for (Button slotButton : slotButtons) {
            slotButton.setText("");
        }
        for (Label resultLabel : resultLabels) {
            resultLabel.setText("");
        }
        for (ToggleButton resultButton : resultButtons) {
            resultButton.setSelected(false);
        }
        displayArea.clear();
        currentSlotLabel.setText("현재 선택된 슬롯 : 1");
    }

    private void loadState() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))){
                System.out.println("Saving state to: " + new File(SAVE_FILE).getAbsolutePath());

                slot1List = (List<Integer>) ois.readObject();
                slot2List = (List<Integer>) ois.readObject();
                slot3List = (List<Integer>) ois.readObject();
                slot4List = (List<Integer>) ois.readObject();
                slot5List = (List<Integer>) ois.readObject();
                slot6List = (List<Integer>) ois.readObject();
                savedCombinations = (Set<String>) ois.readObject();
                currentSlotNumber = ois.readInt();

                handleCurrentSlot(currentSlotNumber);
                updateSlotButtonsText();

                for (Label resultLabel : resultLabels) {
                    resultLabel.setText((String) ois.readObject());
                }

                savedDisplayAreaText = (String) ois.readObject();
                updateDisplayArea(savedDisplayAreaText);
                lottoNumberToggleCheck();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void lottoNumberToggleCheck() {
        for (int i = 0; i < resultButtons.length; i++) {
            resultButtons[i].setSelected(false);
        }

        for (int i = 0; i < resultLabels.length; i++) {
            Integer resultIndex = Integer.valueOf(resultLabels[i].getText());
            resultButtons[resultIndex - 1].setSelected(true);
        }
    }

    private void updateSlotButtonsText(){
        slotButtons[0].setText(slot1List.toString().substring(1, slot1List.toString().length() - 1));
        slotButtons[1].setText(slot2List.toString().substring(1, slot2List.toString().length() - 1));
        slotButtons[2].setText(slot3List.toString().substring(1, slot3List.toString().length() - 1));
        slotButtons[3].setText(slot4List.toString().substring(1, slot4List.toString().length() - 1));
        slotButtons[4].setText(slot5List.toString().substring(1, slot5List.toString().length() - 1));
        slotButtons[5].setText(slot6List.toString().substring(1, slot6List.toString().length() - 1));
    }

    private void updateDisplayArea(String savedDisplayAreaText){
        displayArea.setText(savedDisplayAreaText);
    }
}
