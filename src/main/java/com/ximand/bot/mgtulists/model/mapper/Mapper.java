package com.ximand.bot.mgtulists.model.mapper;

public interface Mapper<FromT, ToT> {

    ToT map(FromT from);

}
