import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class Frame extends JFrame {
    // Массив элементов списка
    public String[] stations = new String[] {"Автово", "Адмиралтейская", "Академическая", "Балтийская", "Бухарестская",
            "Беговая", "Василеостровская", "Владимирская", "Волковская", "Выборгская", "Горьковская", "Гостиный двор",
            "Гражданский проспект", "Девяткино", "Достоевская", "Дунайская", "Елизаровская", "Звездная",
            "Звенигородская", "Зенит", "Кировский завод", "Комендантский проспект", "Крестовский остров",
            "Купчино", "Ладожская", "Ленинский проспект", "Лесная", "Лиговский проспект", "Ломоносовская",
            "Маяковская", "Международная", "Московская", "Московские ворота", "Нарвская", "Невский проспект",
            "Новочеркасская", "Обводный канал", "Обухово", "Озерки", "Парк Победы", "Парнас", "Петроградская",
            "Пионерская", "Площадь Александра Невского 1", "Площадь Александра Невского 2", "Площадь Восстания",
            "Площадь Ленина", "Площадь Мужества", "Политехническая", "Приморская", "Пролетарская",
            "Проспект Большевиков", "Проспект Ветеранов", "Проспект Просвещения", "Проспект Славы",
            "Пушкинская", "Рыбацкое", "Садовая", "Сенная площадь", "Спасская", "Спортивная", "Старая Деревня",
            "Технологический институт 1", "Технологический институт 2", "Удельная", "Улица Дыбенко", "Фрунзенская",
            "Черная речка", "Чернышевская", "Чкаловская", "Шушары", "Электросила"};

    private JComboBox<String> First;
    private JComboBox<String> Second;
    private DefaultComboBoxModel<String> cbModel;
    private DefaultComboBoxModel<String> cbModel2;

    public Frame() {
        super("Метрополитен СПб. Выбор станций");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Модель данных для 1-го списка
        cbModel = new DefaultComboBoxModel<String>();
        for (int i = 0; i < stations.length; i++)
            cbModel.addElement((String)stations[i]);

        // 1-й раскрывающийся список
        First = new JComboBox<String>(cbModel);

        // Модель данных для 2-го списка
        cbModel2 = new DefaultComboBoxModel<String>();
        for (int i = 0; i < stations.length; i++)
            cbModel2.addElement((String)stations[i]);

        // 2-й раскрывающийся список
        Second = new JComboBox<String>(cbModel2);

        JLabel startLabel = new JLabel("Начальная станция:");
        JLabel endLabel = new JLabel("Конечная станция:");

        // Кнопка активирующая расчет минимального пути для выбранных станций
        JButton btnAdd = new JButton("Рассчитать");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrameResult(First.getSelectedItem().toString(), Second.getSelectedItem().toString());
            }
        });

        // Размещение компонентов в интерфейсе и вывод окна
        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout(3, 2, 20, 20));
        contents.add  (startLabel); contents.add  (First );
        contents.add  (endLabel); contents.add  (Second);
        contents.add  (btnAdd  );
        setContentPane(contents);
        setSize(500, 180);
        setVisible(true);

    }

    public void FrameResult(String Start, String End) {
        List<String> list = Calculation.calculation(Start, End);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Минимальный путь от станции " + Start + " до станции " + End + ":\n\n");
        for (String s : list) {
            stringBuilder.append(s).append("");
        }

        JTextArea textArea = new JTextArea(stringBuilder.toString());
        JFrame frame = new JFrame("Метрополитен СПб. Минимальный путь");
        frame.add(new JScrollPane(textArea));
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
    }
}
