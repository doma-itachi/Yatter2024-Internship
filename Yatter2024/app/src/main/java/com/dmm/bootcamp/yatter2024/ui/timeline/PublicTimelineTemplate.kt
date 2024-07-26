package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.AccountBindingModel
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PublicTimelineTemplate (
    statusList: List<StatusBindingModel>,
    account: AccountBindingModel?,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickPost: () -> Unit,
    onClickProfile: () -> Unit,
    onEditProfile: () -> Unit,
    onClickLogout: () -> Unit,
    onClickIcon: (String) -> Unit
){
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)

    ModalDrawer(
        //drawerState = DrawerState(DrawerValue.Open),//あとで消す
        drawerContent = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val context = LocalContext.current
                    val placeholder = ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.avatar_placeholder,
                        null
                    )
                    AsyncImage(
                        modifier = Modifier
                            .size(64.dp),
                        model = ImageRequest.Builder(context)
                            .data(account?.avatar)
                            .placeholder(placeholder)
                            .error(placeholder)
                            .fallback(placeholder)
                            .setHeader("User-Agent", "Mozilla/5.0")
                            .build(),
                        contentDescription = "アバター画像",
                        contentScale = ContentScale.Crop
                    )

                    IconButton(onClick = onEditProfile) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "editProfile")
                    }
                }
                Text(
                    text = account?.displayName?: stringResource(id = R.string.unknown_user),
                    fontSize = 5.em,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "@${account?.username?:""}")

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = buildAnnotatedString { 
                            append(account?.followingCount.toString())
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                                )
                            ){
                                append(stringResource(id = R.string.follow))
                            }
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            append(account?.followerCount.toString())
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                                )
                            ){
                                append(stringResource(id = R.string.follower))
                            }
                        }
                    )
                }
                
                Divider(
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 24.dp)
                )

                TextButton(
                    onClick = onClickProfile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "profile"
                    )
                    Text(text = stringResource(id = R.string.drawer_item_profile))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.public_timeline_header_title))
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onClickPost) {
                    Icon(
                        painter = painterResource(id = R.drawable.post),
                        contentDescription = stringResource(id = R.string.public_timeline_fab_description)
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(statusList) { item ->
                        StatusRow(statusBindingModel = item, onClickIcon = onClickIcon)
                    }
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}

@Preview
@Composable
private fun PublicTimelineTemplatePreview(){
    Yatter2024Theme {
        Surface {
            PublicTimelineTemplate(
                statusList = listOf(
                    StatusBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content1",
                        attachmentMediaList = listOf()
                    ),
                    StatusBindingModel(
                        id = "id2",
                        displayName = "display name2",
                        username = "username2",
                        avatar = null,
                        content = "preview content2",
                        attachmentMediaList = listOf()
                    )
                ),
                account = AccountBindingModel(
                    id = "Meid",
                    username = "ユーザー",
                    displayName = "表示名",
                    avatar = null,
                    followingCount = 120,
                    followerCount = 60
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {},
                onClickPost = {},
                onClickProfile = {},
                onClickLogout = {},
                onEditProfile = {},
                onClickIcon = {}
            )
        }
    }
}

@Composable
fun Drawer(
    content: @Composable () -> Unit
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Text(text = "drawer")
        },
        content = {}
    )
}

//@Preview
//@Composable
//fun DrawerPreview(){
//    Yatter2024Theme {
//
//    }
//}