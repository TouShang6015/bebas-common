# bebas-common
自用common包

# 生成必须的校验文件
mvn install:install-file \
-Dfile=target/org-bebas-common-${version}.jar \
-DpomFile=pom.xml \
-DlocalRepositoryPath=maven-repo \
-DcreateChecksum=true  # 关键！生成 sha1/md5 文件