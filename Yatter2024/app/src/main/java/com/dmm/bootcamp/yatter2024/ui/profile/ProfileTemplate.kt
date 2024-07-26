package com.dmm.bootcamp.yatter2024.ui.profile

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.StatusRow
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
    statusList: List<StatusBindingModel>,
    account: AccountBindingModel?,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickNav: () -> Unit
){
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)

    Scaffold (
        topBar = {
            TopAppBar (
                title = {
                    Text(text = "プロフィール")
                },
                navigationIcon = {
                    IconButton(onClick = onClickNav) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.post_header_back)
                        )
                    }
                }
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState),
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                val placeholder = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.avatar_placeholder,
                    null
                )
                AsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context)
                        .data(account?.avatar.toString())
                        .placeholder(placeholder)
                        .error(placeholder)
                        .fallback(placeholder)
                        .setHeader("User-Agent", "Mozilla/5.0")
                        .build(),
                    contentDescription = "アバター画像",
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = account?.displayName?: stringResource(id = R.string.empty_displayName),
                    fontSize = 6.em,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "@${account?.username?:""}",
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                )
                Spacer(modifier = Modifier.height(12.dp))
                if(account?.note!=null)
                    Text(text = account.note)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            if(account==null)append("")
                            else append(account.followingCount.toString())
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                                )
                            ) {
                                append(stringResource(id = R.string.follow))
                            }
                        }
                    )

                    Text(
                        text = buildAnnotatedString {
                            if(account==null)append("")
                            else append(account.followerCount.toString())
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                                )
                            ) {
                                append(stringResource(id = R.string.follower))
                            }
                        }
                    )
                }
                Divider(modifier = Modifier.padding(12.dp))

                LazyColumn(
                    //modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(statusList){item->
                        StatusRow(statusBindingModel = item, onClickIcon = {})
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            if(isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileTemplatePreview(){
    Yatter2024Theme {
        Surface {
            ProfileTemplate(
                statusList = listOf(),
                account = AccountBindingModel(
                    username = "ユーザー1",
                    displayName = "ユーザー",
                    note = "こんにちは",
                    avatar = null,
                    header = null,
                    followingCount = 120,
                    followerCount = 60
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {},
                onClickNav = {}
            )
        }
    }
}