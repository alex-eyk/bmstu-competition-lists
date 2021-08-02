package com.ximand.bot.mgtulists.telegram.repository;

import com.ximand.bot.mgtulists.telegram.object.TelegramUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TelegramUserRepository extends MongoRepository<TelegramUser, Long> {

}
