package com.xiandong.fst.presenter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.ChooseImageViewModel;
import com.xiandong.fst.model.ChooseImageViewModelImpl;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.view.ChooseImageViewView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/01/05
 */

public class ChooseImageViewPresenterImpl implements ChooseImageViewPresenter {
    ChooseImageViewModel chooseImageViewModel;
    ChooseImageViewView chooseImageViewView;

    public ChooseImageViewPresenterImpl(ChooseImageViewView chooseImageViewView) {
        this.chooseImageViewView = chooseImageViewView;
        this.chooseImageViewModel = new ChooseImageViewModelImpl();
    }

    public List<String> chooseImageView() {
        List<String> words = new ArrayList<>();
        words.add("拍照");
        words.add("相册");
        return words;
    }

    public void upDataUserImg(Bitmap bit) {
        chooseImageViewModel.upDataUserImg(bit, this);
    }

    @Override
    public void upDataUserImgSuccess(String msg) {
        chooseImageViewView.upDataUserImgSuccess(msg);
    }

    @Override
    public void upDataUserImgFails(String err) {
        chooseImageViewView.upDataUserImgFails(err);
    }
}