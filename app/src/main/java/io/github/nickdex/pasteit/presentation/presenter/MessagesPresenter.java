/*
 * Copyright © 2016 Nikhil Warke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nickdex.pasteit.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.di.ViewScope;
import io.github.nickdex.pasteit.core.presentation.DefaultSubscriber;
import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.domain.model.ClipItem;
import io.github.nickdex.pasteit.domain.model.Device;
import io.github.nickdex.pasteit.interactor.message.GetMessages;
import io.github.nickdex.pasteit.interactor.message.PasteClip;
import io.github.nickdex.pasteit.presentation.mapper.MessageModelClipItemMapper;
import io.github.nickdex.pasteit.presentation.model.MessageModel;
import io.github.nickdex.pasteit.presentation.view.MessagesView;

/**
 * Contain simple methods to do operations on message.
 * Ties data to the lifecycle of the view.
 */
@ViewScope
public class MessagesPresenter extends BasePresenter<MessagesView> {

    private GetMessages getMessages;
    private PasteClip pasteClip;

    private MessageModelClipItemMapper mapper;

    private Device device;

    @Inject
    public MessagesPresenter(NetworkManager networkManager,
                             GetMessages getMessages,
                             PasteClip pasteClip,
                             MessageModelClipItemMapper mapper) {
        super(networkManager);
        this.getMessages = getMessages;
        this.pasteClip = pasteClip;
        this.mapper = mapper;
    }

    public void sendMessage(String message) {
        view.showProgress(R.string.sending);
        MessageModel model = new MessageModel();
        model.setDeviceType(Device.PHONE);
        model.setText(message);
        model.setDeviceResId(R.drawable.chip_smartphone);
        model.setTimestamp(System.currentTimeMillis());
        pasteClip.execute(mapper.mapToSecond(model), new DefaultSubscriber<Void>(view) {
            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
                view.hideProgress();
            }

            @Override
            public void onNext(Void aVoid) {
                view.clearInput();
                view.hideProgress();
            }
        });
    }

    private void getMessages() {
        view.showProgress();
        getMessages.execute(getDevice(), new DefaultSubscriber<List<ClipItem>>(view) {

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
                view.hideProgress();
            }

            @Override
            public void onNext(List<ClipItem> clipItems) {
                view.renderMessages(mapper.mapToFirst(clipItems));
            }
        });
    }

    private void setTitle() {
        view.setTitle(getDevice().name());
    }

    @Override
    public void refreshData() {
        getMessages();
        setTitle();
    }

    @Override
    protected void onViewDetached() {
        super.onViewDetached();
        getMessages.unSubscribe();
        pasteClip.unSubscribe();
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        refreshData();
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}