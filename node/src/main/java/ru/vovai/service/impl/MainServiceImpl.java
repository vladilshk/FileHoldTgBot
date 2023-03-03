package ru.vovai.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.vovai.dao.AppUserDAO;
import ru.vovai.dao.RawDataDAO;
import ru.vovai.entity.AppUser;
import ru.vovai.entity.RawData;
import ru.vovai.service.MainService;
import ru.vovai.service.ProducerService;

import static ru.vovai.entity.enums.UserState.BASIC_STATE;
import static ru.vovai.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
import static ru.vovai.service.enums.ServiceCommands.*;

@Service
@Log4j
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService, AppUserDAO appUserDAO) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
    }


    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);

        var userState = appUser.getState();
        var text = update.getMessage().getText();
        var output = "";

        if (CANCEL.equals(text)){
            output = cancelProcess(appUser);
        } else if (BASIC_STATE.equals(userState)){
            output = processServiceCommand(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)){
            //TODO: add email processing
        } else {
            log.error("Unknown user state: " + userState);
            output = "Неизвестная ошибка! Введите /cancel  и попробуйте снова!";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)){
            return;
        }

        //TODO: add document save
        var answer = "Документ успушно загружен! Ссылка для скачивания: http://test.ru/get-doc/777";
        sendAnswer(answer, chatId);
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)){
            return;
        }

        //TODO: add photo save
        var answer = "Фото успушно загружен! Ссылка для скачивания: http://test.ru/get-photo/777";
        sendAnswer(answer, chatId);
    }

    private boolean isNotAllowToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getState();
        if(! appUser.getIsActive()){
            var error = "Зарегистрируйтесь или активируйте свою учетную запись для загрузки котента.";
            return true;
        } else if (!BASIC_STATE.equals(userState)) {
            var error = "Щтмените тукущую команду с помощью /cancel для отправки файлов.";
            return true;
        }
        return false;
    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(output);
        producerService.producerAnswer(sendMessage);
    }

    private String processServiceCommand(AppUser appUser, String cmd) {
        if (REGISTRATION.equals(cmd)){
            //TODO: add registration
            return "Временно недоступно.";
        } else if (HELP.equals(cmd)) {
            return help();
        } else if (START.equals(cmd)) {
            return "Приветствую! Чтобы посмотреть список достуаных команд введите /help";
        } else {
            return  "Неизвестная команда! Чтобы посмотреть список достуаных команд введите /help";
        }
    }

    private String help() {
        return "Список достуаных команд:\n" +
                "/cancel - отмена выполнения тепущей команды;\n" +
                "/registration - регистрация пользователя.;";
    }

    private String cancelProcess(AppUser appUser) {
        appUser.setState(BASIC_STATE);
        appUserDAO.save(appUser);
        return "Команда отменена!";
    }

    private AppUser findOrSaveAppUser(Update update){
        User telegramUser = update.getMessage().getFrom();
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());

        if (persistentAppUser == null){
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .username(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    //TODO: change standart state from true to false
                    .isActive(true)
                    .state(BASIC_STATE)
                    .build();
            return appUserDAO.save(transientAppUser);
        }

        return persistentAppUser;
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }
}